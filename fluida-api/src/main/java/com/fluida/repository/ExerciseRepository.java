package com.fluida.repository;

import com.fluida.model.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
    List<Exercise> findByTrainingId(Long trainingId);
    Optional<Exercise> findByIdAndTrainingId(Long exerciseId, Long trainingId);
}
