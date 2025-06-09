package com.fluida.service;

import com.fluida.model.Customer;
import com.fluida.model.Training;
import com.fluida.repository.CustomerRepository;
import com.fluida.repository.TrainingRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TrainingService {

    private final TrainingRepository trainingRepository;
    private final CustomerRepository customerRepository;

    public Training createTraining(String name, String category, String userEmail) {
        Customer customer = customerRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("Customer not found"));

        Training training = new Training();
        training.setName(name);
        training.setCategory(category);
        training.setCustomer(customer);

        return trainingRepository.save(training);
    }

    public List<Training> getTrainingsByUser(String userEmail) {
        return trainingRepository.findByCustomerEmail(userEmail);
    }

    public Training getTrainingByIdAndUser(Long trainingId, String userEmail) {
        return trainingRepository.findByIdAndCustomerEmail(trainingId, userEmail)
                .orElseThrow(() -> new EntityNotFoundException("Training not found or access denied"));
    }

    public Training updateTraining(Long trainingId, String name, String category, String userEmail) {
        Training training = getTrainingByIdAndUser(trainingId, userEmail);

        training.setName(name);
        training.setCategory(category);

        return trainingRepository.save(training);
    }

    public void deleteTraining(Long trainingId, String userEmail) {
        Training training = getTrainingByIdAndUser(trainingId, userEmail);
        trainingRepository.delete(training);
    }
}
