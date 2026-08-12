package com.example.nova.metadata;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface MetadataMapper {
    ServiceMetadata toEntity(MetadataRequestDTO dto);

    @Mapping(
            target = "instanceId",
            source = "serviceInstance.instanceId"
    )
    MetadataResponseDTO toDTO(ServiceMetadata serviceMetadata);

    void updateEntity(
            MetadataRequestDTO dto,
            @MappingTarget ServiceMetadata metadata
    );
}
