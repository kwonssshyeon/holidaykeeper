package com.planitsquare.holidaykeeper.service;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HolidayInitializer implements ApplicationRunner {
    private final HolidayInitializeService holidayInitializeService;
    @Override
    public void run(ApplicationArguments args) {
        holidayInitializeService.initCountriesAndHolidays().block();
    }
}