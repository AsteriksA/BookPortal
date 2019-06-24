package com.gold.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.Date;

@Getter
@RequiredArgsConstructor(staticName = "of")
public class ErrorResponse {

    // HTTP Response Status Code
    private final HttpStatus status;
    // General Error message
    private final String message;
    // Error code
    private final ErrorCode errorCode;
    private final Date timestamp;
}
