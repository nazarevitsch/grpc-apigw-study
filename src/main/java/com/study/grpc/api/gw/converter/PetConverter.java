package com.study.grpc.api.gw.converter;

import com.study.grpc.account.client.Pet;
import com.study.grpc.api.gw.dto.PetView;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class PetConverter {

    public List<Pet> fromViewToDto(List<PetView> pets) {
        return pets.stream()
                .map(this::fromViewToDto)
                .toList();
    }

    public Pet fromViewToDto(PetView pet) {
        Pet.Builder builder = Pet.newBuilder();
        Optional.ofNullable(pet.getId()).ifPresent(builder::setId);
        Optional.ofNullable(pet.getName()).ifPresent(builder::setName);
        return builder.build();
    }

    public List<PetView> fromDtoToView(List<Pet> dtos) {
        return dtos.stream()
                .map(this::fromDtoToView)
                .toList();
    }

    public PetView fromDtoToView(Pet dto) {
        PetView pet = new PetView();
        if (dto.hasId()) pet.setId(dto.getId());
        if (dto.hasName()) pet.setName(dto.getName());
        if (dto.hasOwnerId()) pet.setOwnerId(dto.getOwnerId());
        return pet;
    }
}
