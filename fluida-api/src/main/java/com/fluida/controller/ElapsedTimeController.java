package com.fluida.controller;

import com.fluida.dto.AddElapsedTimeRequest;
import com.fluida.dto.ElapsedTimeResponse;
import com.fluida.mapper.ElapsedTimeMapper;
import com.fluida.model.ElapsedTime;
import com.fluida.service.ElapsedTimeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trainings/{trainingId}/exercises/{exerciseId}/times")
@RequiredArgsConstructor
@Slf4j
public class ElapsedTimeController {

    private final ElapsedTimeService elapsedTimeService;

    @PostMapping
    public ResponseEntity<ElapsedTimeResponse> addElapsedTime(
            @PathVariable Long trainingId,
            @PathVariable Long exerciseId,
            @RequestBody AddElapsedTimeRequest request,
            Authentication auth) {
        log.info("Adding elapsed time {}s to exercise {} for user: {}",
                request.timeElapsedInSeconds(), exerciseId, auth.getName());

        ElapsedTime elapsedTime = elapsedTimeService.addElapsedTime(
                trainingId, exerciseId, request.timeElapsedInSeconds(), auth.getName());

        return ResponseEntity.status(HttpStatus.CREATED).body(ElapsedTimeMapper.toResponse(elapsedTime));
    }

    @GetMapping
    public ResponseEntity<List<ElapsedTimeResponse>> getElapsedTimes(
            @PathVariable Long trainingId,
            @PathVariable Long exerciseId,
            Authentication auth) {
        log.info("Getting elapsed times for exercise {} and user: {}", exerciseId, auth.getName());

        List<ElapsedTime> times = elapsedTimeService.getElapsedTimesByExercise(
                trainingId, exerciseId, auth.getName());

        List<ElapsedTimeResponse> response = times.stream()
                .map(ElapsedTimeMapper::toResponse)
                .toList();

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{timeId}")
    public ResponseEntity<Void> deleteElapsedTime(
            @PathVariable Long trainingId,
            @PathVariable Long exerciseId,
            @PathVariable Long timeId,
            Authentication auth) {
        log.info("Deleting elapsed time {} from exercise {} for user: {}",
                timeId, exerciseId, auth.getName());

        elapsedTimeService.deleteElapsedTime(trainingId, exerciseId, timeId, auth.getName());
        return ResponseEntity.noContent().build();
    }
}