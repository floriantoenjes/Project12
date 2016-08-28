package com.floriantoenjes.step;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StepService {
    @Autowired
    StepRepository stepRepository;

    public void save(Step step) {
        stepRepository.save(step);
    }

    public Step findById(Long id) {
        return stepRepository.findOne(id);
    }

    public List<Step> findAll() {
        return (List<Step>) stepRepository.findAll();
    }

}
