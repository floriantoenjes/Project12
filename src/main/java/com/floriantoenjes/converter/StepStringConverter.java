package com.floriantoenjes.converter;

import com.floriantoenjes.step.Step;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class StepStringConverter implements Converter<Step, String> {

    @Override
    public String convert(Step source) {
        return source.getDescription();
    }

    @Bean
    public ConversionService getConversionService() {
        ConversionServiceFactoryBean bean = new ConversionServiceFactoryBean();
        Set<Converter> converters = new HashSet<>();
        converters.add(new StepStringConverter());
        bean.setConverters(converters);
        return bean.getObject();
    }
}


