package com.example.nova.discovery;

import com.example.nova.core.ServiceInstance;
import com.example.nova.core.ServiceInstanceRepository;
import com.example.nova.core.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DiscoveryService {

    private final ServiceInstanceRepository serviceInstanceRepository;
    private final DiscoveryDTOMapper mapper;

    public List<DiscoveryDTO> discover(String serviceName , Status status){
        return serviceInstanceRepository.findByServiceNameAndStatus(serviceName,status).stream().map(mapper::toDTO).collect(Collectors.toList());
    }

    public List<DiscoveryDTO> getALlInstances(){
        return serviceInstanceRepository.findAll().stream().map(mapper::toDTO).toList();
    }

}
