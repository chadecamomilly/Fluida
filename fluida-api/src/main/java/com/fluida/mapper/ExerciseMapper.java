package com.fluida.mapper;

import com.fluida.dto.ElapsedTimeResponse;
import com.fluida.model.Exercise;
import com.fluida.dto.ExerciseResponse;
import java.util.List;

public class ExerciseMapper {

    public static ExerciseResponse toResponse(Exercise exercise) {
        List<ElapsedTimeResponse> times = exercise.getElapsedTimeHistory() != null
                ? exercise.getElapsedTimeHistory().stream()
                .map(ElapsedTimeMapper::toResponse)
                .toList()
                : List.of();

        return new ExerciseResponse(
                exercise.getId(),
                exercise.getCreatedAt(),
                exercise.getName(),
                times
        );
    }

    public static ExerciseResponse toSimpleResponse(Exercise exercise) {
        return new ExerciseResponse(
                exercise.getId(),
                exercise.getCreatedAt(),
                exercise.getName(),
                List.of()
        );
    }
}