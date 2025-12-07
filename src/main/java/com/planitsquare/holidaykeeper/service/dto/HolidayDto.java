package com.planitsquare.holidaykeeper.service.dto;

import com.planitsquare.holidaykeeper.domain.Holiday;

import java.time.LocalDate;

public record HolidayDto (
        Long id,
        LocalDate date,
        String name,
        String localName,
        String countryCode,
        boolean fixed,
        boolean global,
        String[] counties,
        String[] types
) {
    public static HolidayDto from(Holiday holiday) {
        return new HolidayDto(
                holiday.getId(),
                holiday.getDate(),
                holiday.getName(),
                holiday.getLocalName(),
                holiday.getCountry().getCode(),
                holiday.isFixed(),
                holiday.isGlobal(),
                holiday.getCounties().stream()
                        .map(c -> c.getId().getCountyCode())
                        .toArray(String[]::new),
                holiday.getTypes().stream()
                        .map(t -> t.getId().getTypeName())
                        .toArray(String[]::new)
        );
    }
}