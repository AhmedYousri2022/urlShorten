package com.movinx.urlshortening.dto;

public record ErrorResponseDto(

        int status,

        String message
) {
}
