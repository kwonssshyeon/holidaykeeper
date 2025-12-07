package com.planitsquare.holidaykeeper.global.response;

import lombok.Getter;

@Getter
public enum CustomCode {
    OK(200, "요청이 성공적으로 처리되었습니다."),
    CREATED(201, "리소스가 성공적으로 생성되었습니다."),
    UPDATED(200, "리소스가 성공적으로 수정되었습니다."),
    DELETED(200, "리소스가 성공적으로 삭제되었습니다."),

    INVALID_REQUEST(400, "잘못된 요청입니다."),
    INTERNAL_SERVER_ERROR(500, "서버 내부 오류입니다.");

    private final int status;
    private final String message;

    CustomCode(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
