package com.fluida.controller;

import com.fluida.dto.CreateTrainingRequest;
import com.fluida.dto.TrainingResponse;
import com.fluida.mapper.TrainingMapper;
import com.fluida.model.Training;
import com.fluida.service.TrainingService;
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
@RequestMapping("/api/trainings")
@RequiredArgsConstructor
@Slf4j
public class TrainingController {

    private final TrainingService trainingService;

    @PostMapping
    public ResponseEntity<TrainingResponse> createTraining(@RequestBody CreateTrainingRequest request, Authentication auth) {
        log.info("Creating training '{}' for user: {}", request.name(), auth.getName());
        Training training = trainingService.createTraining(request.name(), request.category(), auth.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(TrainingMapper.toResponse(training));
    }

    @GetMapping
    public ResponseEntity<List<TrainingResponse>> getAllTrainings(Authentication auth) {
        log.info("Getting all trainings for user: {}", auth.getName());
        List<Training> trainings = trainingService.getTrainingsByUser(auth.getName());

        List<TrainingResponse> response = trainings.stream()
                .map(TrainingMapper::toResponse)
                .toList();

        return ResponseEntity.ok(response);
    }

    // READ ONE
    @GetMapping("/{id}")
    public ResponseEntity<TrainingResponse> getTraining(@PathVariable Long id, Authentication auth) {
        log.info("Getting training {} for user: {}", id, auth.getName());
        Training training = trainingService.getTrainingByIdAndUser(id, auth.getName());
        return ResponseEntity.ok(TrainingMapper.toResponse(training));
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<TrainingResponse> updateTraining(
            @PathVariable Long id,
            @RequestBody CreateTrainingRequest request,
            Authentication auth) {
        log.info("Updating training {} for user: {}", id, auth.getName());
        Training training = trainingService.updateTraining(id, request.name(), request.category(), auth.getName());
        return ResponseEntity.ok(TrainingMapper.toResponse(training));
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTraining(@PathVariable Long id, Authentication auth) {
        log.info("Deleting training {} for user: {}", id, auth.getName());
        trainingService.deleteTraining(id, auth.getName());
        return ResponseEntity.noContent().build();
    }
}