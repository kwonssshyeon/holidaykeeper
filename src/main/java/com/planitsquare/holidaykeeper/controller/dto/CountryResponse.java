package com.planitsquare.holidaykeeper.controller.dto;

import com.planitsquare.holidaykeeper.service.dto.CountryDto;

public record CountryResponse(
        String code,
        String name
) {
    public static CountryResponse from(CountryDto dto) {
        return new CountryResponse(dto.code(), dto.name());
    }
}
