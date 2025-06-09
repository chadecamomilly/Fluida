package com.fluida.dto;

public record LoginResponse(
        CustomerResponse customer,
        String authHeader,
        int totalTrainings) {
}
