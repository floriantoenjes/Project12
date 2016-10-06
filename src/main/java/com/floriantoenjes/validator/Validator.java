package com.floriantoenjes.validator;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Component
public class Validator {

    @Bean
    public org.springframework.validation.Validator localValidatorFactoryBean() {
        return new LocalValidatorFactoryBean();
    }

}
