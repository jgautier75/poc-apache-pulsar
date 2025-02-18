package com.acme.jga.pulsar.consumers.impl;

import com.acme.jga.pulsar.consumers.api.IConsumerFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.pulsar.client.api.SubscriptionType;
import org.springframework.pulsar.annotation.PulsarListener;
import org.springframework.pulsar.core.PulsarTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ConsumerFacade implements IConsumerFacade {
    private final PulsarTemplate<String> pulsarTemplate;

    @PulsarListener(topics = "topic1", subscriptionName = "consumer-topic1", subscriptionType = SubscriptionType.Exclusive)
    public void consumeTopic1(String message) {
        log.info("Consuming [{}]", message);
    }

}
