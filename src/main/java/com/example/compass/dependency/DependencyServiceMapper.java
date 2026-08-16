package com.example.compass.dependency;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DependencyServiceMapper {
    DependencyResponseDTO toDTO(ServiceDependency serviceDependency);
}
