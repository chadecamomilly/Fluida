package com.fluida.mapper;

import com.fluida.dto.ExerciseResponse;
import com.fluida.model.Training;
import com.fluida.dto.TrainingResponse;
import java.util.Set;
import java.util.stream.Collectors;

public class TrainingMapper {

    public static TrainingResponse toResponse(Training training) {
        Set<ExerciseResponse> exercises = training.getExercises() != null
                ? training.getExercises().stream()
                .map(ExerciseMapper::toResponse)
                .collect(Collectors.toSet())
                : Set.of();

        return new TrainingResponse(
                training.getId(),
                training.getCreatedAt(),
                training.getName(),
                training.getCategory(),
                exercises
        );
    }

    public static TrainingResponse toSimpleResponse(Training training) {
        return new TrainingResponse(
                training.getId(),
                training.getCreatedAt(),
                training.getName(),
                training.getCategory(),
                Set.of()
        );
    }
}