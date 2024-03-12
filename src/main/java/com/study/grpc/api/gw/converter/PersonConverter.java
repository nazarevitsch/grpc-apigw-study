package com.study.grpc.api.gw.converter;

import com.study.grpc.account.client.Person;
import com.study.grpc.account.client.Persons;
import com.study.grpc.api.gw.dto.PersonView;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PersonConverter {

    private final PetConverter petConverter;

    public Person fromViewToDto(PersonView person) {
        Person.Builder builder = Person.newBuilder();
        Optional.ofNullable(person.getId()).ifPresent(builder::setId);
        Optional.ofNullable(person.getFirstName()).ifPresent(builder::setFirstName);
        Optional.ofNullable(person.getLastName()).ifPresent(builder::setLastName);
        Optional.ofNullable(person.getEmail()).ifPresent(builder::setEmail);
        Optional.ofNullable(person.getAge()).ifPresent(builder::setAge);
        Optional.ofNullable(person.getPets()).ifPresent(pets -> builder.addAllPets(this.petConverter.fromViewToDto(pets)));
        return builder.build();
    }

    public PersonView fromDtoToView(Person dto) {
        PersonView person = new PersonView();
        if (dto.hasId()) person.setId(dto.getId());
        if (dto.hasFirstName()) person.setFirstName(dto.getFirstName());
        if (dto.hasLastName()) person.setLastName(dto.getLastName());
        if (dto.hasEmail()) person.setEmail(dto.getEmail());
        if (dto.hasAge()) person.setAge(dto.getAge());
        person.setPets(this.petConverter.fromDtoToView(dto.getPetsList()));
        return person;
    }

    public List<PersonView> fromDtoToView(Persons dtos) {
        return dtos.getPersonsList()
                .stream()
                .map(this::fromDtoToView)
                .toList();
    }
}
