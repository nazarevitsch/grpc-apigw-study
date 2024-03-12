package com.study.grpc.api.gw.service;

import com.study.grpc.account.client.Order;
import com.study.grpc.account.client.OrderServiceGrpc;
import com.study.grpc.account.client.Orders;
import com.study.grpc.api.gw.converter.OrderConverter;
import com.study.grpc.api.gw.dto.OrderView;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final static int MILLISECONDS_TO_WAIT = 9999;

    @GrpcClient("order-service")
    private OrderServiceGrpc.OrderServiceStub asyncClient;

    private final OrderConverter converter;

    public List<OrderView> generateOrders(Integer count) {
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        Orders.Builder builder = Orders.newBuilder();

        OrderGenerator generator = new OrderGenerator();
        StreamObserver<Order> observer = asyncClient.create(new StreamObserver<>() {
            @Override
            public void onNext(Orders value) {
                builder.addAllOrders(value.getOrdersList());
            }

            @Override
            public void onError(Throwable t) {
                countDownLatch.countDown();
            }

            @Override
            public void onCompleted() {
                countDownLatch.countDown();
                generator.stop();
                log.info("Completed !!!");
            }
        });
        generator.setObserverAndCount(observer, count);
        generator.start();
        try {
            generator.join();
        } catch (InterruptedException e) {}
        boolean await = false;
        try {
            await = countDownLatch.await(1, TimeUnit.MINUTES);
        } catch (Exception e) {}
        return await ? converter.fromDtoToView(builder.build()) : Collections.emptyList();
    }

    public List<OrderView> getByRandomFilters() {
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        Orders.Builder builder = Orders.newBuilder();
        StreamObserver<Order> responseObserver = asyncClient.getWithFilter(new StreamObserver<Order>() {
            @Override
            public void onNext(Order order) {
                log.info("Received order with id: {}, brand: {}, state: {}.", order.getId(), order.getBrand(), order.getState());
                builder.addOrders(order);
            }

            @Override
            public void onError(Throwable throwable) {
                countDownLatch.countDown();
            }

            @Override
            public void onCompleted() {
                countDownLatch.countDown();
                log.info("Completed !!!");
            }
        });
        for (int i = 0; i < 6; i++) {
            String brand = Randomizer.randomBrand();
            String state = Randomizer.randomState();
            responseObserver.onNext(Order.newBuilder().setBrand(brand).setState(state).build());
            log.info("Searching order's set: {} by brand: {} and state: {}", i, brand, state);
            sleep();
        }
        responseObserver.onCompleted();
        boolean await = false;
        try {
            await = countDownLatch.await(1, TimeUnit.MINUTES);
        } catch (Exception e) {}
        return await ? converter.fromDtoToView(builder.build()) : Collections.emptyList();
    }

    private static void sleep() {
        try {
            Thread.sleep(MILLISECONDS_TO_WAIT);
        } catch (InterruptedException e) {}
    }
}
