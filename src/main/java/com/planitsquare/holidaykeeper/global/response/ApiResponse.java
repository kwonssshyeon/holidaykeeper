package com.planitsquare.holidaykeeper.global.response;

public record ApiResponse<T>(
        int status,
        String message,
        T data
) {
    public static <T> ApiResponse<T> ok(T data) {
        return ApiResponse.of(CustomCode.OK, data);
    }

    public static <T> ApiResponse<T> error(CustomCode code) {
        return new ApiResponse<>(code.getStatus(), code.getMessage(), null);
    }

    public static <T> ApiResponse<T> of(CustomCode code, T data) {
        return new ApiResponse<>(code.getStatus(), code.getMessage(), data);
    }

    public static <T> ApiResponse<T> of(CustomCode code) {
        return new ApiResponse<>(code.getStatus(), code.getMessage(), null);
    }
}
