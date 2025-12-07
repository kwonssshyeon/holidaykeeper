package com.planitsquare.holidaykeeper.service.mapper;

import com.planitsquare.holidaykeeper.domain.Country;
import com.planitsquare.holidaykeeper.external.dto.CountryDto;

public class CountryMapper {
    private CountryMapper() {}

    public static Country toEntity(CountryDto dto) {
        return Country.of(dto.countryCode(), dto.name());
    }
}
