package com.planitsquare.holidaykeeper.service.dto;

import com.planitsquare.holidaykeeper.domain.Country;

public record CountryDto(
        String code,
        String name
) {
    public static CountryDto from(Country country) {
        return new CountryDto(country.getCode(), country.getName());
    }
}
