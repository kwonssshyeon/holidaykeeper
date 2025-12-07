package com.planitsquare.holidaykeeper.service;

import com.planitsquare.holidaykeeper.domain.Country;
import com.planitsquare.holidaykeeper.domain.Holiday;
import com.planitsquare.holidaykeeper.external.HolidayApiClient;
import com.planitsquare.holidaykeeper.external.dto.CountryDto;
import com.planitsquare.holidaykeeper.external.dto.HolidayDto;
import com.planitsquare.holidaykeeper.repository.CountryRepository;
import com.planitsquare.holidaykeeper.repository.HolidayRepository;
import com.planitsquare.holidaykeeper.service.mapper.CountryMapper;
import com.planitsquare.holidaykeeper.service.mapper.HolidayMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.Year;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HolidayInitializeService {
    private final HolidayApiClient holidayClient;
    private final CountryRepository countryRepository;
    private final HolidayRepository holidayRepository;

    private static final int COUNTRY_CONCURRENCY = 5;   // 국가 병렬처리 수
    private static final int YEAR_CONCURRENCY = 5;      // 연도 병렬처리 수
    private static final int YEAR_COUNT = 5;           // 최근 5년
    private static final int START_YEAR = Year.now().getValue() - YEAR_COUNT + 1;

    public Mono<Void> initCountriesAndHolidays() {
        return holidayClient.getCountries()
                .flatMapMany(Flux::fromIterable)
                // Thread 5개로 국가별 병렬처리
                .flatMap(this::syncCountryFull, COUNTRY_CONCURRENCY)
                .then();
    }

    /** 국가 단위로 한번에 처리 */
    private Mono<Void> syncCountryFull(CountryDto countryDto) {
        Country country = CountryMapper.toEntity(countryDto);

        return saveCountry(country)
                .thenMany(
                        Flux.range(START_YEAR, YEAR_COUNT)
                                // 5년치 데이터 병렬로 가져오기
                                .flatMap(year -> holidayClient.getHolidays(country.getCode(), year), YEAR_CONCURRENCY)
                                .flatMap(Flux::fromIterable)
                                .collectList()
                )
                .flatMap(dtos -> saveCountryHolidaysBatch(country, dtos))
                .then();
    }

    /** 국가 한 번 저장 (블로킹 I/O 분리) */
    private Mono<Void> saveCountry(Country country) {
        return Mono.fromRunnable(() -> countryRepository.save(country))
                .subscribeOn(Schedulers.boundedElastic())
                .then();
    }

    /** Holiday 국가 단위 batch 저장 (블로킹 I/O 분리) */
    private Mono<Void> saveCountryHolidaysBatch(Country country, List<HolidayDto> dtos) {
        // 중복 제거
        // TODO: 더 나은 중복 제거 방법 고민
        List<HolidayDto> distinct = dtos.stream()
                .collect(Collectors.toMap(
                        dto -> dto.date() + dto.name(),
                        dto -> dto,
                        (a, b) -> a
                ))
                .values().stream().toList();

        return Mono.fromRunnable(() -> {
                    List<Holiday> entities = distinct.stream()
                            .map(dto -> HolidayMapper.toEntity(dto, country))
                            .collect(Collectors.toList());
                    holidayRepository.saveAll(entities);
                })
                .subscribeOn(Schedulers.boundedElastic())
                .then();
    }
}