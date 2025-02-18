package com.acme.jga.pulsar.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.pulsar.core.PulsarAdministration;

@Configuration
public class PulsarConfig {

    @Autowired
    private AppPulsarValues appPulsarValues;

    @Bean
    public PulsarAdministration pulsarAdministration() {
        return new PulsarAdministration(appPulsarValues.getAdminUrl());
    }

}
