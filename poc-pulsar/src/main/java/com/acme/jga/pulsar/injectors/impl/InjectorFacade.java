package com.acme.jga.pulsar.injectors.impl;

import com.acme.jga.pulsar.injectors.api.IInjectorFacade;
import com.acme.jga.pulsar.producers.api.IProducerFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicBoolean;

@Service
@RequiredArgsConstructor
public class InjectorFacade implements IInjectorFacade {
    private final IProducerFacade producerFacade;
    private final long WAIT_MS = 1000L;

    private final AtomicBoolean topic1Run = new AtomicBoolean(false);
    private final AtomicBoolean topic1BisRun = new AtomicBoolean(false);
    private final AtomicBoolean topic2Run = new AtomicBoolean(false);

    @Override
    public void startTopic1Injector() {
        startInjector(topic1Run, producerFacade::sendTopic1);
    }

    @Override
    public void stopTopic1Injector() {
        stopInjector(topic1Run);
    }

    @Override
    public void startTopic1BisInjector() {
        startInjector(topic1BisRun, producerFacade::sendTopic1BisWithKey);
    }

    @Override
    public void stopTopic1BisInjector() {
        stopInjector(topic1BisRun);
    }

    @Override
    public void startTopic2Injector() {
        startInjector(topic2Run, producerFacade::sendTopic2);
    }

    @Override
    public void stopTopic2Injector() {
        stopInjector(topic2Run);
    }

    // Method to start a generic injector
    private void startInjector(AtomicBoolean runFlag, Runnable task) {
        runFlag.set(true);
        Thread virtualThread = Thread.startVirtualThread(() -> {
            while (runFlag.get()) {
                task.run();
                try {
                    Thread.sleep(WAIT_MS);
                } catch (InterruptedException e) {
                    // Silent catch
                }
            }
        });
        try {
            virtualThread.join();  // Wait for the virtual thread to finish execution
        } catch (InterruptedException e) {
            System.out.println("Main thread interrupted");
        }
    }

    // Method to stop a generic injector
    private void stopInjector(AtomicBoolean runFlag) {
        runFlag.set(false);
    }
}
