package com.acme.jga.pulsar.injectors.api;

public interface IInjectorFacade {

    void startTopic1Injector();

    void stopTopic1Injector();

    void startTopic1BisInjector();

    void stopTopic1BisInjector();

    void startTopic2Injector();

    void stopTopic2Injector();
}
