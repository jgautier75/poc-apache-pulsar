package com.acme.jga.pulsar.rest;

import com.acme.jga.pulsar.injectors.api.IInjectorFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PulsarController {
    private final IInjectorFacade injectorFacade;

    @PostMapping(value = "/api/v1/topic1")
    public ResponseEntity<Void> startTopic1Injection() {
        injectorFacade.startTopic1Injector();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(value = "/api/v1/topic1")
    public ResponseEntity<Void> stopTopic1Injection() {
        injectorFacade.stopTopic1Injector();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping(value = "/api/v1/topic1Bis")
    public ResponseEntity<Void> startTopic1BisInjection() {
        injectorFacade.startTopic1BisInjector();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(value = "/api/v1/topic1Bis")
    public ResponseEntity<Void> stopTopic1BisInjection() {
        injectorFacade.stopTopic1BisInjector();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping(value = "/api/v1/topic2")
    public ResponseEntity<Void> startTopic2Injection() {
        injectorFacade.startTopic2Injector();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(value = "/api/v1/topic2")
    public ResponseEntity<Void> stopTopic2Injection() {
        injectorFacade.stopTopic2Injector();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
