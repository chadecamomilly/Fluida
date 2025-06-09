package com.fluida.dto;

import java.time.LocalDateTime;
import java.util.List;

public record ExerciseResponse(
        Long id,
        LocalDateTime createdAt,
        String name,
        List<ElapsedTimeResponse> elapsedTimeHistory
) {}

