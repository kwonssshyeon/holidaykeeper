package com.planitsquare.holidaykeeper.global.exception;

import com.planitsquare.holidaykeeper.global.response.ApiResponse;
import com.planitsquare.holidaykeeper.global.response.CustomCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ApiResponse<CustomCode>> handleCustomException(CustomException ex) {
        CustomCode customCode = ex.getCustomCode();
        ApiResponse<CustomCode> response = ApiResponse.of(customCode);
        return ResponseEntity.status(customCode.getStatus()).body(response);
    }
}
