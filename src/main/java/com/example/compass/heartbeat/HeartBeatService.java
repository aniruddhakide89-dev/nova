package com.example.compass.heartbeat;

import com.example.compass.core.ServiceInstance;
import com.example.compass.core.ServiceInstanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class HeartBeatService {

    private final ServiceInstanceRepository serviceInstanceRepository;

    @Transactional
    public void heartbeat(UUID instanceId){
        ServiceInstance serviceInstance = serviceInstanceRepository.findById(instanceId).orElseThrow(() -> new RuntimeException("did not find"));
        serviceInstance.setLastHeartbeat(Instant.now());
        serviceInstanceRepository.save(serviceInstance);
    }

}
