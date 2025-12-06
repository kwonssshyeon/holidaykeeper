package com.planitsquare.holidaykeeper.external;

import com.planitsquare.holidaykeeper.external.dto.CountryDto;
import com.planitsquare.holidaykeeper.external.dto.HolidayDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

import static reactor.util.retry.Retry.backoff;

@Component
@RequiredArgsConstructor
public class HolidayApiClient {
    private final WebClient holidayWebClient;

    public Mono<List<CountryDto>> getCountries() {
        return holidayWebClient.get()
                .uri("/AvailableCountries")
                .retrieve()
                .bodyToFlux(CountryDto.class)
                .collectList()
                .retryWhen(backoff(1, java.time.Duration.ofSeconds(1)));
    }

    public Mono<List<HolidayDto>> getHolidays(String countryCode, int year) {
        return holidayWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/PublicHolidays/{year}/{country}")
                        .build(year, countryCode))
                .retrieve()
                .bodyToFlux(HolidayDto.class)
                .collectList()
                .retryWhen(backoff(1, java.time.Duration.ofSeconds(1)));
    }
}
