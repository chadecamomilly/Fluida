package com.fluida.dto;
import java.time.LocalDateTime;
import java.util.Set;

public record TrainingResponse(Long id,
                               LocalDateTime createdAt,
                               String name,
                               String category,
                               Set<ExerciseResponse> exercises) {
}
