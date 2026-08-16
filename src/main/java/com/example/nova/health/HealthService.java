package com.example.nova.health;

import java.util.List;
import java.util.UUID;

import com.example.nova.core.ServiceInstance;
import com.example.nova.core.ServiceInstanceRepository;
import com.example.nova.core.Status;
import com.example.nova.dependency.ServiceDependency;
import com.example.nova.dependency.ServiceDependencyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HealthService {

    private final ServiceInstanceRepository serviceInstanceRepository;
    private final ServiceDependencyRepository serviceDependencyRepository;

    public List<HealthResponseDTO> getServiceHealth(String serviceName){
        List<ServiceInstance> serviceInstances = serviceInstanceRepository.findByServiceName(serviceName);
        return serviceInstances.stream().map(this::buildHealthResponse).toList();
    }

    public HealthResponseDTO getInstanceHealthById(UUID instanceId){
        ServiceInstance serviceInstance = serviceInstanceRepository.findById(instanceId).orElseThrow(() -> new RuntimeException("No Instance Found"));
        return buildHealthResponse(serviceInstance);
    }

    private HealthResponseDTO buildHealthResponse(ServiceInstance instance){
        String serviceName = instance.getServiceName();

        List<ServiceDependency> dependencies =
                serviceDependencyRepository
                        .findBySourceService(serviceName);

        int healthyDependencies = 0;
        int unhealthyDependencies = 0;

        for (ServiceDependency dependency : dependencies) {

            String targetService = dependency.getTargetService();

            List<ServiceInstance> targetInstances =
                    serviceInstanceRepository
                            .findByServiceName(targetService);

            boolean healthy = targetInstances.stream()
                    .anyMatch(target ->
                            target.getStatus() == Status.UP);

            if (healthy) {
                healthyDependencies++;
            } else {
                unhealthyDependencies++;
            }
        }


        return HealthResponseDTO.builder()
                        .serviceName(serviceName)
                        .instanceId(instance.getInstanceId())
                        .status(instance.getStatus())
                        .lastHeartBeat(instance.getLastHeartbeat())
                        .registeredAt(instance.getRegisteredAt())
                        .healthyDependencies(healthyDependencies)
                        .unhealthyDependencies(unhealthyDependencies)
                        .build();

    }
}
