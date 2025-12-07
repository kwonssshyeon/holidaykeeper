package com.planitsquare.holidaykeeper.controller.dto;

import com.planitsquare.holidaykeeper.service.dto.HolidayDto;

public record HolidayDetailResponse(
        Long id,
        String date,
        String name,
        String localName,
        String countryCode,
        boolean fixed,
        boolean global,
        String[] counties,
        String[] types
) {
    public static HolidayDetailResponse from(HolidayDto dto) {
        return new HolidayDetailResponse(
                dto.id(),
                dto.date().toString(),
                dto.name(),
                dto.localName(),
                dto.countryCode(),
                dto.fixed(),
                dto.global(),
                dto.counties(),
                dto.types()
        );
    }
}
