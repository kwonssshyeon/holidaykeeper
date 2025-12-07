package com.planitsquare.holidaykeeper.global.exception;

import com.planitsquare.holidaykeeper.global.response.CustomCode;
import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {
    private final CustomCode customCode;

    public CustomException(CustomCode customCode) {
        super(customCode.getMessage());
        this.customCode = customCode;
    }

    public CustomException(CustomCode customCode, String message) {
        super(message);
        this.customCode = customCode;
    }
}
