package com.floriantoenjes.core;

import org.hibernate.annotations.GeneratorType;

import javax.annotation.Generated;
import javax.persistence.*;

@MappedSuperclass
public class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private final Long id;

    @Version
    private Long version;

    public BaseEntity() {
        id = null;
    }
}
