package com.planitsquare.holidaykeeper.service.mapper;

import com.planitsquare.holidaykeeper.domain.Country;
import com.planitsquare.holidaykeeper.domain.Holiday;
import com.planitsquare.holidaykeeper.external.dto.HolidayDto;

import java.time.LocalDate;

public final class HolidayMapper {
    private HolidayMapper() {}

    public static Holiday toEntity(HolidayDto dto, Country country) {
        Holiday holiday = Holiday.of(LocalDate.parse(dto.date()), dto.name(), dto.localName(),
                country, dto.fixed(), dto.global(), dto.launchYear());
        if (dto.counties() != null) {
            for (String countyCode : dto.counties()) {
                holiday.addCounty(countyCode);
            }
        }
        if (dto.types() != null) {
            for (String typeName : dto.types()) {
                holiday.addType(typeName);
            }
        }
        return holiday;
    }
}