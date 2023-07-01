package com.example.carrentalproject.mapper;

import com.example.carrentalproject.domain.Client;
import com.example.carrentalproject.dto.ClientDto;

public interface Mapper<E, D> {

   D fromEntityToDto(E entity);

   E fromDtoToEntity(D dto);


}
