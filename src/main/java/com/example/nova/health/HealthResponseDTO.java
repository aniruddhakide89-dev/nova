package com.example.nova.health;

import com.example.nova.core.Status;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
public class HealthResponseDTO {
    private String serviceName;
    private UUID instanceId;
    private Status status;
    private Instant lastHeartBeat;
    private Instant registeredAt;
    private Integer healthyDependencies;
    private Integer unhealthyDependencies;
}
