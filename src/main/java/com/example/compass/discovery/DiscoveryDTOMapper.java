package com.example.compass.discovery;

import com.example.compass.core.ServiceInstance;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DiscoveryDTOMapper {
    DiscoveryDTO toDTO(ServiceInstance serviceInstance);
}
