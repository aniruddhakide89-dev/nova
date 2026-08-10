package com.example.nova.discovery;

import com.example.nova.core.ServiceInstance;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DiscoveryDTOMapper {
    DiscoveryDTO toDTO(ServiceInstance serviceInstance);
}
