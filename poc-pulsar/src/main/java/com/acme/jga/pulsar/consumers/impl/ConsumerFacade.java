package com.acme.jga.pulsar.consumers.impl;

import com.acme.jga.pulsar.consumers.api.IConsumerFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.pulsar.client.api.SubscriptionType;
import org.springframework.messaging.Message;
import org.springframework.pulsar.annotation.PulsarListener;
import org.springframework.pulsar.core.PulsarTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ConsumerFacade implements IConsumerFacade {
    private static final String KEY_HEADER = "pulsar_message_key";
    private final PulsarTemplate<String> pulsarTemplate;

    @PulsarListener(topics = "topic1", subscriptionName = "consumer-1-topic1", subscriptionType = SubscriptionType.Shared)
    public void consumeTopic1Sub1(String message) {
        log.info("consumer-1-topic1 - consuming [{}]", message);
    }

    @PulsarListener(topics = "topic1", subscriptionName = "consumer-1-topic1", subscriptionType = SubscriptionType.Shared)
    public void consumeTopic1Sub2(String message) {
        log.info("consumer-1-topic1-copy - consuming [{}]", message);
    }

    @PulsarListener(topics = "topic1Bis", subscriptionName = "consumer-1-key-1-topic1-bis", subscriptionType = SubscriptionType.Key_Shared)
    public void consumeTopic1Sub1Key1(Message<String> record) {
        log.info("consumer-1-key-1-topic1-bis - consuming [{}] - [{}]", record.getHeaders().get(KEY_HEADER), record.getPayload());
    }

    @PulsarListener(topics = "topic1Bis", subscriptionName = "consumer-1-key-1-topic1-bis", subscriptionType = SubscriptionType.Key_Shared)
    public void consumeTopic1Sub1Key2(Message<String> record) {
        log.info("consumer-1-key-2-topic1-bis - consuming [{}] - [{}]", record.getHeaders().get(KEY_HEADER), record.getPayload());
    }

}
