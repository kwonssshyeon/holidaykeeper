package com.planitsquare.holidaykeeper.service;

import com.planitsquare.holidaykeeper.domain.Country;
import com.planitsquare.holidaykeeper.repository.CountryRepository;
import com.planitsquare.holidaykeeper.service.dto.CountryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HolidaySearchOptionService {
    private final CountryRepository countryRepository;

    public List<CountryDto> getAllCountries() {
        List<Country> countries = countryRepository.findAll();
        return countries.stream()
                .map(CountryDto::from)
                .toList();
    }

    /** 모든 휴일 유형을 반환합니다.
     * DISTINCT 키워드를 통해 DB에서 가져온 값을 임시로 하드코딩
     */
    public List<String> getAllHolidayTypes() {
        return List.of("Authorities", "Bank", "Observance", "Optional", "Public", "School");
    }
}
