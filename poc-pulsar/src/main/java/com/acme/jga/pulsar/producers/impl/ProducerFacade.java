package com.acme.jga.pulsar.producers.impl;

import com.acme.jga.pulsar.producers.api.IProducerFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.pulsar.core.PulsarTemplate;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ProducerFacade implements IProducerFacade {
    private final PulsarTemplate<String> pulsarTemplate;

    public void sendTopic1(){
        pulsarTemplate.send("topic1","topic1_" + Long.toString(System.currentTimeMillis()));
    }

}
