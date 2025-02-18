package com.acme.jga.pulsar.rest;

import com.acme.jga.pulsar.injectors.api.IInjectorFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PulsarController {
    private final IInjectorFacade injectorFacade;

    @PostMapping(value = "/api/v1/topic1")
    public void startTopic1Injection() {
        injectorFacade.startTopic1Injector();
    }

    @DeleteMapping(value = "/api/v1/topic1")
    public void stopTopic1Injection() {
        injectorFacade.stopTopic1Injector();
    }

}
