package com.fluida.repository;

import com.fluida.model.Training;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TrainingRepository extends JpaRepository<Training, Long> {
    List<Training> findByCustomerEmail(String email);
    Optional<Training> findByIdAndCustomerEmail(Long id, String email);
}
