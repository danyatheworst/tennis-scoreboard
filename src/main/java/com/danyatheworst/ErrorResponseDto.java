package com.danyatheworst;

import lombok.AllArgsConstructor;


public class ErrorResponseDto {
    public final String message;

    public ErrorResponseDto(String message) {
        this.message = message;
    }
}
