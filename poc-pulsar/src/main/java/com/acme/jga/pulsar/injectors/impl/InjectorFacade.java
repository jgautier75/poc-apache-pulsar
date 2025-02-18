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
        topic1Run.set(true);
        Thread virtualThread = Thread.startVirtualThread(() -> {
            while (topic1Run.get()) {
                producerFacade.sendTopic1();
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

    @Override
    public void stopTopic1Injector() {
        topic1Run.set(false);
    }

    @Override
    public void startTopic1BisInjector() {
        topic1BisRun.set(true);
        Thread virtualThread = Thread.startVirtualThread(() -> {
            while (topic1BisRun.get()) {
                producerFacade.sendTopic1BisWithKey();
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

    @Override
    public void stopTopic1BisInjector() {
        topic1BisRun.set(false);
    }

    @Override
    public void startTopic2Injector() {
        topic2Run.set(true);
        Thread virtualThread = Thread.startVirtualThread(() -> {
            while (topic2Run.get()) {
                producerFacade.sendTopic2();
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

    @Override
    public void stopTopic2Injector() {
        topic2Run.set(false);
    }
}
