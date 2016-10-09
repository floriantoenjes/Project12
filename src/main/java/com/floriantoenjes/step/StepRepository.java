package com.floriantoenjes.step;

import com.floriantoenjes.core.BaseEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;

public interface StepRepository extends CrudRepository<Step, Long>{

    // Prevent deletion of steps through REST
    @Override
    @RestResource(exported = false)
    void delete(Step entity);
}
