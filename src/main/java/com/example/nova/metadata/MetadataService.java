package com.example.nova.metadata;

import com.example.nova.core.ServiceInstance;
import com.example.nova.core.ServiceInstanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MetadataService {

    private final MetadataServiceRepository metadataServiceRepository;
    private final ServiceInstanceRepository serviceInstanceRepository;
    private final MetadataMapper metadataMapper;

    @Transactional
    public MetadataResponseDTO registerMetadata(UUID instanceId, MetadataRequestDTO metadataRequestDTO) {
        ServiceInstance serviceInstance = serviceInstanceRepository.findById(instanceId).orElseThrow(() -> new RuntimeException("No such service"));
        ServiceMetadata serviceMetadata = metadataMapper.toEntity(metadataRequestDTO);
        serviceMetadata.setServiceInstance(serviceInstance);
        metadataServiceRepository.save(serviceMetadata);
        return metadataMapper.toDTO(serviceMetadata);
    }

    public MetadataResponseDTO getMetadataById(UUID instanceId) {
        return metadataMapper.toDTO(metadataServiceRepository.findById(instanceId).orElseThrow(() -> new RuntimeException("No such instance")));
    }

    @Transactional
    public void deregisterMetadata(UUID instanceId) {
        metadataServiceRepository.deleteById(instanceId);
    }

    @Transactional
    public MetadataResponseDTO updateMetadata(
            UUID instanceId,
            MetadataRequestDTO dto) {

        ServiceMetadata metadata =
                metadataServiceRepository.findById(instanceId)
                        .orElseThrow(() ->
                                new RuntimeException("Metadata not found"));

        metadataMapper.updateEntity(dto, metadata);

        metadataServiceRepository.save(metadata);

        return metadataMapper.toDTO(metadata);
    }
}
