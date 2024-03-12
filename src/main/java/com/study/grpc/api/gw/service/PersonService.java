package com.study.grpc.api.gw.service;

import com.study.grpc.account.client.*;
import com.study.grpc.api.gw.converter.PersonConverter;
import com.study.grpc.api.gw.dto.PersonView;
import io.grpc.Deadline;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class PersonService {

    private final PersonConverter converter;
    @GrpcClient("account-service")
    private PersonServiceGrpc.PersonServiceBlockingStub client;
    @GrpcClient("account-service")
    private PersonServiceGrpc.PersonServiceStub asyncClient;

    public PersonView create(PersonView person) {
        Person personRequest = this.converter.fromViewToDto(person);
        Person savedPerson = client.withDeadline(Deadline.after(1, TimeUnit.SECONDS)).create(personRequest);
        return this.converter.fromDtoToView(savedPerson);
    }

    public PersonView get(Long id) {
        Person person = client.get(Id.newBuilder().setId(id).build());
        return this.converter.fromDtoToView(person);
    }

    public List<PersonView> getAll(List<Long> ids) {
        Persons persons = client.getByIds(Ids.newBuilder().addAllIds(ids).build());
        return this.converter.fromDtoToView(persons);
    }

    public void delete(Long id) {
        client.delete(Id.newBuilder().setId(id).build());
    }


    public List<PersonView> getAllStream() {
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        final List<PersonView> response = new LinkedList<>();

        asyncClient.getPersonsStream(Empty.getDefaultInstance(), new StreamObserver<>() {
            @Override
            public void onNext(Person dto) {
                response.add(converter.fromDtoToView(dto));
                log.info("Received person with id: {}", dto.getId());
            }

            @Override
            public void onError(Throwable throwable) {
                countDownLatch.countDown();
            }

            @Override
            public void onCompleted() {
                countDownLatch.countDown();
            }
        });
        boolean await = false;
        try {
            await = countDownLatch.await(1, TimeUnit.MINUTES);
        } catch (Exception e) {}
        return await ?  response : Collections.emptyList();
    }
}
