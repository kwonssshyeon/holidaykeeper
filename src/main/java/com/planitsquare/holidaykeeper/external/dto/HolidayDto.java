package com.planitsquare.holidaykeeper.external.dto;


public record HolidayDto(
        String date,
        String name,
        String localName,
        String countryCode,
        Boolean fixed,
        Boolean global,
        Integer launchYear,
        String[] counties,
        String[] types
) {
}
