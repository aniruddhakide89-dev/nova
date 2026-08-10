package com.example.nova.registration;

import com.example.nova.core.ServiceInstance;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RegistrationDTOMapper {
    RegistrationResponseDTO toDTO(ServiceInstance serviceInstance);
}
