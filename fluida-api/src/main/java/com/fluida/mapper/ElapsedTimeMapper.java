package com.fluida.mapper;

import com.fluida.model.ElapsedTime;
import com.fluida.dto.ElapsedTimeResponse;

public class ElapsedTimeMapper {

    public static ElapsedTimeResponse toResponse(ElapsedTime elapsedTime) {
        return new ElapsedTimeResponse(
                elapsedTime.getId(),
                elapsedTime.getTimeElapsedInSeconds(),
                elapsedTime.getCreatedAt(),
                elapsedTime.getExercise().getId()
        );
    }
}