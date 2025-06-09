package com.fluida.service;

import com.fluida.model.Exercise;
import com.fluida.model.Training;
import com.fluida.repository.ExerciseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExerciseService {

    private final ExerciseRepository exerciseRepository;
    private final TrainingService trainingService;

    public Exercise createExercise(Long trainingId, String name, String userEmail) {
        // Valida se o training pertence ao usuário
        Training training = trainingService.getTrainingByIdAndUser(trainingId, userEmail);

        Exercise exercise = new Exercise();
        exercise.setName(name);
        exercise.setTraining(training);

        return exerciseRepository.save(exercise);
    }

    public List<Exercise> getExercisesByTraining(Long trainingId, String userEmail) {
        // Valida se o training pertence ao usuário
        trainingService.getTrainingByIdAndUser(trainingId, userEmail);

        return exerciseRepository.findByTrainingId(trainingId);
    }

    public Exercise getExerciseByIdAndTraining(Long exerciseId, Long trainingId, String userEmail) {
        // Valida se o training pertence ao usuário
        trainingService.getTrainingByIdAndUser(trainingId, userEmail);

        return exerciseRepository.findByIdAndTrainingId(exerciseId, trainingId)
                .orElseThrow(() -> new EntityNotFoundException("Exercise not found in this training"));
    }

    public Exercise updateExercise(Long exerciseId, Long trainingId, String name, String userEmail) {
        Exercise exercise = getExerciseByIdAndTraining(exerciseId, trainingId, userEmail);

        exercise.setName(name);

        return exerciseRepository.save(exercise);
    }

    public void deleteExercise(Long exerciseId, Long trainingId, String userEmail) {
        Exercise exercise = getExerciseByIdAndTraining(exerciseId, trainingId, userEmail);
        exerciseRepository.delete(exercise);
    }

    public Exercise getExerciseById(Long exerciseId, String userEmail) {
        Exercise exercise = exerciseRepository.findById(exerciseId)
                .orElseThrow(() -> new EntityNotFoundException("Exercise not found"));

        // Valida se o exercise pertence ao usuário através do training
        trainingService.getTrainingByIdAndUser(exercise.getTraining().getId(), userEmail);

        return exercise;
    }
}