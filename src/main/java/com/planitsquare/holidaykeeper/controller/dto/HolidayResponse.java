package com.planitsquare.holidaykeeper.controller.dto;

import com.planitsquare.holidaykeeper.service.dto.HolidayDto;

public record HolidayResponse(
        Long id,
        String date,
        String name,
        String localName,
        String countryCode,
        boolean fixed
) {
    public static HolidayResponse from(HolidayDto dto) {
        return new HolidayResponse(
                dto.id(),
                dto.date().toString(),
                dto.name(),
                dto.localName(),
                dto.countryCode(),
                dto.fixed()
        );
    }
}