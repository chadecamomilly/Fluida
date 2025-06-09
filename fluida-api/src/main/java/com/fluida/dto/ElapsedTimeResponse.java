package com.fluida.dto;

import java.time.LocalDateTime;

public record ElapsedTimeResponse(
        Long id,
        Integer timeElapsedInSeconds,
        LocalDateTime createdAt,
        Long exerciseId
) {}
