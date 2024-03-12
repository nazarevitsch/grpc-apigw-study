package com.study.grpc.api.gw.service;

import com.study.grpc.account.client.Order;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OrderGenerator extends Thread {

    private final static int MILLISECONDS_TO_WAIT = 2;

    private StreamObserver<Order> observer;
    private int count;

    @Override
    public synchronized void run() {
        for (int i = 0; i < count; i++) {
            observer.onNext(Order
                    .newBuilder()
                    .setBrand(Randomizer.randomBrand())
                    .setState(Randomizer.randomState())
                    .setPrice(Randomizer.randomPrice())
                    .build());
            log.info("Order with N: {} was generated.", i);
            sleep();
        }
        observer.onCompleted();
    }

    public void setObserverAndCount(StreamObserver<Order> observer, int count) {
        this.observer = observer;
        this.count = count;
    }

    private static void sleep() {
        try {
            sleep(MILLISECONDS_TO_WAIT);
        } catch (Exception e) {}
    }
}
