// Service
package com.fluida.service;

import com.fluida.model.ElapsedTime;
import com.fluida.model.Exercise;
import com.fluida.repository.ElapsedTimeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ElapsedTimeService {

    private final ElapsedTimeRepository elapsedTimeRepository;
    private final ExerciseService exerciseService;

    public ElapsedTime addElapsedTime(Long trainingId, Long exerciseId, Integer timeInSeconds, String userEmail) {
        // Valida se o exercise pertence ao usuário
        Exercise exercise = exerciseService.getExerciseByIdAndTraining(exerciseId, trainingId, userEmail);

        ElapsedTime elapsedTime = new ElapsedTime();
        elapsedTime.setTimeElapsedInSeconds(timeInSeconds);
        elapsedTime.setExercise(exercise);

        return elapsedTimeRepository.save(elapsedTime);
    }

    // READ ALL
    public List<ElapsedTime> getElapsedTimesByExercise(Long trainingId, Long exerciseId, String userEmail) {
        // Valida se o exercise pertence ao usuário
        exerciseService.getExerciseByIdAndTraining(exerciseId, trainingId, userEmail);

        return elapsedTimeRepository.findByExerciseIdOrderByCreatedAtDesc(exerciseId);
    }

    // DELETE
    public void deleteElapsedTime(Long trainingId, Long exerciseId, Long timeId, String userEmail) {
        // Valida se o exercise pertence ao usuário
        exerciseService.getExerciseByIdAndTraining(exerciseId, trainingId, userEmail);

        ElapsedTime elapsedTime = elapsedTimeRepository.findByIdAndExerciseId(timeId, exerciseId)
                .orElseThrow(() -> new EntityNotFoundException("Elapsed time not found in this exercise"));

        elapsedTimeRepository.delete(elapsedTime);
    }
}