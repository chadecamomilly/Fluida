package com.fluida.controller;

import com.fluida.dto.CreateExerciseRequest;
import com.fluida.dto.ExerciseResponse;
import com.fluida.dto.UpdateExerciseRequest;
import com.fluida.mapper.ExerciseMapper;
import com.fluida.model.Exercise;
import com.fluida.service.ExerciseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/trainings/{trainingId}/exercises")
@RequiredArgsConstructor
@Slf4j
public class ExercisesController {

    private final ExerciseService exerciseService;

    // CREATE
    @PostMapping
    public ResponseEntity<ExerciseResponse> createExercise(
            @PathVariable Long trainingId,
            @RequestBody CreateExerciseRequest request,
            Authentication auth) {
        log.info("Creating exercise '{}' for training {} and user: {}", request.name(), trainingId, auth.getName());
        Exercise exercise = exerciseService.createExercise(trainingId, request.name(), auth.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(ExerciseMapper.toResponse(exercise));
    }

    // READ ALL
    @GetMapping
    public ResponseEntity<List<ExerciseResponse>> getExercises(
            @PathVariable Long trainingId,
            Authentication auth) {
        log.info("Getting exercises for training {} and user: {}", trainingId, auth.getName());
        List<Exercise> exercises = exerciseService.getExercisesByTraining(trainingId, auth.getName());

        List<ExerciseResponse> response = exercises.stream()
                .map(ExerciseMapper::toResponse)
                .toList();

        return ResponseEntity.ok(response);
    }

    // READ ONE
    @GetMapping("/{exerciseId}")
    public ResponseEntity<ExerciseResponse> getExercise(
            @PathVariable Long trainingId,
            @PathVariable Long exerciseId,
            Authentication auth) {
        log.info("Getting exercise {} from training {} for user: {}", exerciseId, trainingId, auth.getName());
        Exercise exercise = exerciseService.getExerciseByIdAndTraining(exerciseId, trainingId, auth.getName());
        return ResponseEntity.ok(ExerciseMapper.toResponse(exercise));
    }

    // UPDATE
    @PutMapping("/{exerciseId}")
    public ResponseEntity<ExerciseResponse> updateExercise(
            @PathVariable Long trainingId,
            @PathVariable Long exerciseId,
            @RequestBody UpdateExerciseRequest request,
            Authentication auth) {
        log.info("Updating exercise {} from training {} for user: {}", exerciseId, trainingId, auth.getName());
        Exercise exercise = exerciseService.updateExercise(exerciseId, trainingId, request.name(), auth.getName());
        return ResponseEntity.ok(ExerciseMapper.toResponse(exercise));
    }

    // DELETE
    @DeleteMapping("/{exerciseId}")
    public ResponseEntity<Void> deleteExercise(
            @PathVariable Long trainingId,
            @PathVariable Long exerciseId,
            Authentication auth) {
        log.info("Deleting exercise {} from training {} for user: {}", exerciseId, trainingId, auth.getName());
        exerciseService.deleteExercise(exerciseId, trainingId, auth.getName());
        return ResponseEntity.noContent().build();
    }
}