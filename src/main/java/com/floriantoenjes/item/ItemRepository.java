package com.floriantoenjes.item;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;

public interface ItemRepository extends CrudRepository<Item, Long>{

    // Prevent deletion of items via REST
    @Override
    @RestResource(exported = false)
    void delete(Item entity);
}
