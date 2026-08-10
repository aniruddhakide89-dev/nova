package com.example.nova.registration;

import com.example.nova.core.ServiceInstance;
import com.example.nova.core.ServiceInstanceRepository;
import com.example.nova.core.Status;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RegistrationService {

    private final ServiceInstanceRepository serviceInstanceRepository;
    private final RegistrationDTOMapper mapper;

    @Transactional
    public RegistrationResponseDTO registerServiceInstance(RegistrationRequestDTO dto){
        ServiceInstance serviceInstance = new ServiceInstance();
        serviceInstance.setInstanceId(UUID.randomUUID());
        serviceInstance.setServiceName(dto.getServiceName());
        serviceInstance.setHost(dto.getHost());
        serviceInstance.setPort(dto.getPort());
        serviceInstance.setScheme(dto.getScheme());
        serviceInstance.setStatus(Status.UP);
        serviceInstance = serviceInstanceRepository.save(serviceInstance);
        return mapper.toDTO(serviceInstance);
    }

    @Transactional
    public void deregisterServiceInstance(UUID instanceId){
        serviceInstanceRepository.deleteById(instanceId);
    }

}
