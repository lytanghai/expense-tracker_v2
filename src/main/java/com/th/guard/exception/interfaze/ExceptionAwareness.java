package com.th.guard.exception.interfaze;

import org.springframework.http.HttpStatus;

public interface ExceptionAwareness {

    String getCode();

    HttpCodeAwareness getSystemCode();

    default HttpStatus getHttpStatus() {
        return HttpStatus.BAD_REQUEST;
    }
}