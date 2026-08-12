package com.example.nova.heartbeat;

import com.example.nova.core.ServiceInstance;
import com.example.nova.core.ServiceInstanceRepository;
import com.example.nova.core.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;

@Component
@RequiredArgsConstructor
public class HeartBeatScheduler {

    private final ServiceInstanceRepository serviceInstanceRepository;

    @Scheduled(fixedRate = 10000)
    public void checkInactiveInstances() {
        Instant instant = Instant.now().minusSeconds(90);

        List<ServiceInstance> serviceInstances = serviceInstanceRepository.findByStatusAndLastHeartbeatBefore(Status.UP,instant);

        for (ServiceInstance serviceInstance : serviceInstances){
            serviceInstance.setStatus(Status.DOWN);
        }

        serviceInstanceRepository.saveAll(serviceInstances);
    }

}
