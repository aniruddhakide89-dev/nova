package com.example.nova.registration;

import com.example.nova.core.Scheme;
import com.example.nova.core.Status;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
public class RegistrationResponseDTO {
    private UUID instanceId;
    private String serviceName;
    private Scheme scheme;
    private Status status;
    private String host;
    private Integer port;
    private Instant registeredAt;
    private Instant lastHeartbeat;
}
