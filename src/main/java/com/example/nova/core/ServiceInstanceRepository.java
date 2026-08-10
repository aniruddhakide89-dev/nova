package com.example.nova.core;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ServiceInstanceRepository extends JpaRepository<ServiceInstance,UUID>{
    List<ServiceInstance> findByServiceNameAndStatus(String serviceName , Status status);
}
