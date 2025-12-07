package com.planitsquare.holidaykeeper.controller;

import com.planitsquare.holidaykeeper.controller.dto.CountryResponse;
import com.planitsquare.holidaykeeper.global.response.ApiResponse;
import com.planitsquare.holidaykeeper.service.HolidaySearchOptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.planitsquare.holidaykeeper.global.response.CustomCode.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class HolidayController {
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
}
