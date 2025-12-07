package com.planitsquare.holidaykeeper.controller;

import com.planitsquare.holidaykeeper.controller.dto.CountryResponse;
import com.planitsquare.holidaykeeper.controller.dto.HolidayResponse;
import com.planitsquare.holidaykeeper.global.response.ApiResponse;
import com.planitsquare.holidaykeeper.global.response.PageResponse;
import com.planitsquare.holidaykeeper.service.HolidaySearchOptionService;
import com.planitsquare.holidaykeeper.service.HolidaySearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.planitsquare.holidaykeeper.global.response.CustomCode.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class HolidayController {
    private final HolidaySearchService holidaySearchService;
    private final HolidaySearchOptionService holidaySearchOptionService;

    @GetMapping("/countries")
    public ResponseEntity<ApiResponse<List<CountryResponse>>> getCountries() {
        var result = holidaySearchOptionService.getAllCountries();
        List<CountryResponse> response = result.stream()
                .map(CountryResponse::from)
                .toList();
        return ResponseEntity.ok(ApiResponse.of(OK,response));
    }

    @GetMapping("/holiday-types")
    public ResponseEntity<ApiResponse<List<String>>> getHolidayTypes() {
        var result = holidaySearchOptionService.getAllHolidayTypes();
        return ResponseEntity.ok(ApiResponse.of(OK, result));
    }

    @GetMapping("/holidays")
    public ResponseEntity<ApiResponse<PageResponse<HolidayResponse>>> getHolidays(
            @RequestParam(name = "countryCode", required = false) String countryCode,
            @RequestParam(name = "year", required = false) Integer year,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "20") int size
    ) {
        var result = holidaySearchService.getAllHolidays(countryCode, year, page, size);
        PageResponse<HolidayResponse> response = PageResponse.from(result, HolidayResponse::from);
        return ResponseEntity.ok(ApiResponse.of(OK, response));
    }
}
