package com.planitsquare.holidaykeeper.service;

import com.planitsquare.holidaykeeper.domain.Holiday;
import com.planitsquare.holidaykeeper.global.exception.CustomException;
import com.planitsquare.holidaykeeper.repository.HolidayRepository;
import com.planitsquare.holidaykeeper.service.dto.HolidayDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

import static com.planitsquare.holidaykeeper.global.response.CustomCode.NOT_FOUND;

@Service
@RequiredArgsConstructor
public class HolidaySearchService {
    private final HolidayRepository holidayRepository;

    public Page<HolidayDto> getAllHolidays(String countryCode, Integer year, int page, int size) {
        LocalDate start = (year == null) ? null : LocalDate.of(year, 1, 1);
        LocalDate end   = (year == null) ? null : LocalDate.of(year, 12, 31);

        Page<Holiday> holidays = holidayRepository.searchHolidaysByCountryCodeAndYear(countryCode, start, end, PageRequest.of(page, size));
        return holidays.map(HolidayDto::from);
    }

    public HolidayDto getHolidayById(Long id) {
        Holiday holiday = holidayRepository.findByIdWithTypesAndCounties(id)
                .orElseThrow(() -> new CustomException(NOT_FOUND));
        return HolidayDto.from(holiday);
    }
}
