package com.example.compass.metadata;


import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface MetadataServiceRepository extends JpaRepository<ServiceMetadata, UUID> {
}
