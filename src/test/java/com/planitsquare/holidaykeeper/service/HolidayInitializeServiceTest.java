package com.planitsquare.holidaykeeper.service;

import com.planitsquare.holidaykeeper.domain.Country;
import com.planitsquare.holidaykeeper.domain.Holiday;
import com.planitsquare.holidaykeeper.external.HolidayApiClient;
import com.planitsquare.holidaykeeper.external.dto.CountryDto;
import com.planitsquare.holidaykeeper.external.dto.HolidayDto;
import com.planitsquare.holidaykeeper.repository.CountryRepository;
import com.planitsquare.holidaykeeper.repository.HolidayRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Mockito.*;

class HolidayInitializeServiceTest {
    @Mock
    private HolidayApiClient holidayClient;
    @Mock
    private CountryRepository countryRepository;
    @Mock
    private HolidayRepository holidayRepository;
    private HolidayInitializeService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        service = new HolidayInitializeService(holidayClient, countryRepository, holidayRepository);
    }

    @Test
    @DisplayName("단일 국가와 해당 국가의 휴일 데이터를 초기화하고 저장하는지 검증")
    void initCountriesAndHolidays_shouldCallSaveMethods() {
        CountryDto countryDto = new CountryDto("KR", "Korea");
        HolidayDto holidayDto = new HolidayDto(
                "2025-01-01", "New Year's Day", "New Year",
                "KR", true, true, null, null, new String[] {"Public"});
        when(holidayClient.getCountries())
                .thenReturn(Mono.just(List.of(countryDto)));
        when(holidayClient.getHolidays(eq("KR"), anyInt()))
                .thenReturn(Mono.just(List.of(holidayDto)));

        service.initCountriesAndHolidays().block();

        // Country 저장 호출 검증
        verify(countryRepository, atLeastOnce()).save(any(Country.class));
        // Holiday 저장 호출 검증
        verify(holidayRepository, atLeastOnce()).saveAll(anyList());
    }

    @Test
    @DisplayName("여러 국가의 공휴일을 조회했을때 각 국가별로 저장하는지 검증")
    void initCountriesAndHolidays_shouldCallDifferentCountry() {
        CountryDto kr = new CountryDto("KR", "Korea");
        CountryDto us = new CountryDto("US", "United States");
        HolidayDto krHoliday = new HolidayDto(
                "2025-01-01", "New Year's Day", "New Year",
                "KR", true, true, null, null, new String[]{"Public"}
        );
        HolidayDto usHoliday = new HolidayDto(
                "2025-07-04", "Independence Day", "Independence",
                "US", true, true, null, null, new String[]{"Public"}
        );

        when(holidayClient.getCountries())
                .thenReturn(Mono.just(List.of(kr, us)));

        // KR, US 각각 Holiday 조회하도록 스텁
        when(holidayClient.getHolidays(eq("KR"), anyInt()))
                .thenReturn(Mono.just(List.of(krHoliday)));
        when(holidayClient.getHolidays(eq("US"), anyInt()))
                .thenReturn(Mono.just(List.of(usHoliday)));


        service.initCountriesAndHolidays().block();

        // Country 저장 총 2회
        verify(countryRepository, times(2)).save(any(Country.class));

        // 각 국가별 Holiday 저장이 최소 1번씩 호출되었는지
        verify(holidayRepository, atLeast(2)).saveAll(anyList());
    }

    @Test
    @DisplayName("한 국가의 공휴일 저장시 다중 값 타입 필드도 저장하는지 검증")
    void initCountriesAndHolidays_shouldSaveMultiValueType() {
        CountryDto countryDto = new CountryDto("KR", "Korea");
        HolidayDto holidayDto = new HolidayDto(
                "2025-01-01", "New Year's Day", "New Year",
                "KR", true, true, null, new String[] {"KR-DG", "KR-SU"}, new String[] {"School", "Hospital"});
        when(holidayClient.getCountries())
                .thenReturn(Mono.just(List.of(countryDto)));
        when(holidayClient.getHolidays(eq("KR"), anyInt()))
                .thenReturn(Mono.just(List.of(holidayDto)));

        service.initCountriesAndHolidays().block();

        // Country 저장 호출
        verify(countryRepository, atLeastOnce()).save(any(Country.class));

        // Holiday 저장 호출 + ArgumentCaptor로 실제 저장된 객체 확인
        ArgumentCaptor<List<Holiday>> captor = ArgumentCaptor.forClass(List.class);
        verify(holidayRepository, atLeastOnce()).saveAll(captor.capture());

        List<Holiday> saved = captor.getValue();
        assertThat(saved).hasSize(1);

        Holiday h = saved.get(0);

        // counties 필드 검증
        assertThat(h.getCounties())
                .extracting(c -> c.getId().getCountyCode())
                .containsExactlyInAnyOrder("KR-DG", "KR-SU");

        // types 필드 검증
        assertThat(h.getTypes())
                .extracting(t -> t.getId().getTypeName())
                .containsExactlyInAnyOrder("School", "Hospital");
    }
}