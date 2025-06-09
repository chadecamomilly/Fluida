package com.fluida.repository;

import com.fluida.model.ElapsedTime;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ElapsedTimeRepository extends JpaRepository<ElapsedTime, Long> {
    List<ElapsedTime> findByExerciseIdOrderByCreatedAtDesc(Long exerciseId);
    Optional<ElapsedTime> findByIdAndExerciseId(Long timeId, Long exerciseId);
}