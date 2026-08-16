package com.example.compass.dependency;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ServiceDependencyRepository extends JpaRepository<ServiceDependency, UUID> {
    Optional<ServiceDependency> findBySourceServiceAndTargetService(
            String sourceService,
            String targetService
    );

    List<ServiceDependency> findBySourceService(String sourceService);

    List<ServiceDependency> findByTargetService(String targetService);
}
