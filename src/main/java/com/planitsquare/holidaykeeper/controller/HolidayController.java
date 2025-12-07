package com.planitsquare.holidaykeeper.controller;

import com.planitsquare.holidaykeeper.controller.dto.CountryResponse;
import com.planitsquare.holidaykeeper.controller.dto.HolidayDetailResponse;
import com.planitsquare.holidaykeeper.controller.dto.HolidayResponse;
import com.planitsquare.holidaykeeper.global.response.ApiResponse;
import com.planitsquare.holidaykeeper.global.response.PageResponse;
import com.planitsquare.holidaykeeper.service.HolidaySearchOptionService;
import com.planitsquare.holidaykeeper.service.HolidaySearchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.planitsquare.holidaykeeper.global.response.CustomCode.*;

@Tag(name = "User", description = "사용자 정보 관련 API")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class HolidayController {
    private final HolidaySearchService holidaySearchService;
    private final HolidaySearchOptionService holidaySearchOptionService;

    @GetMapping("/countries")
    @Operation(summary="국가 목록 조회", description="지원하는 국가 목록을 조회합니다.")
    public ResponseEntity<ApiResponse<List<CountryResponse>>> getCountries() {
        var result = holidaySearchOptionService.getAllCountries();
        List<CountryResponse> response = result.stream()
                .map(CountryResponse::from)
                .toList();
        return ResponseEntity.ok(ApiResponse.of(OK,response));
    }

    @GetMapping("/holiday-types")
    @Operation(summary="휴일 유형 목록 조회", description="지원하는 휴일 유형 목록을 조회합니다.")
    public ResponseEntity<ApiResponse<List<String>>> getHolidayTypes() {
        var result = holidaySearchOptionService.getAllHolidayTypes();
        return ResponseEntity.ok(ApiResponse.of(OK, result));
    }

    @GetMapping("/holidays")
    @Operation(summary="휴일 목록 조회", description="휴일 목록을 조회합니다. 국가 코드와 연도를 선택적으로 필터링할 수 있습니다.")
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

    @GetMapping("/holidays/{holidayId}")
    @Operation(summary="휴일 상세 조회", description="특정 휴일의 상세 정보를 조회합니다.")
    public ResponseEntity<ApiResponse<HolidayDetailResponse>> getHolidayDetail(
            @PathVariable(name = "holidayId") Long holidayId
    ) {
        var result = holidaySearchService.getHolidayById(holidayId);
        HolidayDetailResponse response = HolidayDetailResponse.from(result);
        return ResponseEntity.ok(ApiResponse.of(OK, response));
    }
}
