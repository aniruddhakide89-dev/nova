package com.example.compass.registration;

import com.example.compass.core.ServiceInstance;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RegistrationDTOMapper {
    RegistrationResponseDTO toDTO(ServiceInstance serviceInstance);
}
