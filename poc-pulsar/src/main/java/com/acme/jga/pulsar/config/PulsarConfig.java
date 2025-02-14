package com.acme.jga.pulsar.config;

import org.apache.pulsar.client.api.PulsarClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.pulsar.core.*;

@Configuration
public class PulsarConfig {

    @Autowired
    private AppPulsarValues appPulsarValues;

    @Bean
    public PulsarTemplate<String> pulsarTemplate(PulsarProducerFactory<String> pulsarProducerFactory) {
        return new PulsarTemplate<>(pulsarProducerFactory);
    }

    @Bean
    public PulsarProducerFactory<String> pulsarProducerFactory(PulsarClient pulsarClient) {
        return new DefaultPulsarProducerFactory<>(pulsarClient);
    }

    @Bean
    public PulsarClient pulsarClient(PulsarClientFactory pulsarClientFactory) {
        return pulsarClientFactory.createClient();
    }

    @Bean
    public PulsarClientFactory pulsarClientFactory() {
        return new DefaultPulsarClientFactory(appPulsarValues.getUrl());
    }

    @Bean
    public PulsarAdministration pulsarAdministration() {
        return new PulsarAdministration(appPulsarValues.getAdminUrl());
    }

}
