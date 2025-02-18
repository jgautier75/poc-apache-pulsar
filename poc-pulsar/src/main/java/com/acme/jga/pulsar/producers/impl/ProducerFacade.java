package com.acme.jga.pulsar.producers.impl;

import com.acme.jga.pulsar.producers.api.IProducerFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.pulsar.core.PulsarTemplate;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

@RequiredArgsConstructor
@Service
public class ProducerFacade implements IProducerFacade {
    private final PulsarTemplate<String> pulsarTemplate;

    public void sendTopic1() {
        pulsarTemplate.send("topic1", "topic1_" + formatUsingDateTimeFormatter(LocalDateTime.now()));
    }

    @Override
    public void sendTopic2() {
        pulsarTemplate.send("topic2", "topic2_" + formatUsingDateTimeFormatter(LocalDateTime.now()));
    }

    @Override
    public void sendTopic1BisWithKey() {
        pulsarTemplate.send("topic1Bis", "topic1Bis_" + generateKeyPrefix() + formatUsingDateTimeFormatter(LocalDateTime.now()));
    }

    private String generateKeyPrefix() {
        SecureRandom sr = new SecureRandom();
        return "Key" + sr.nextInt(1, 3) + "_";
    }

    private String formatUsingDateTimeFormatter(LocalDateTime localDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX");
        return localDate.atOffset(ZoneOffset.UTC).format(formatter);
    }

}

