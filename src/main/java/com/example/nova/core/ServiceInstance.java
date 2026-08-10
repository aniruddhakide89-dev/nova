package com.example.nova.core;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "service_instances")
@Data
@NoArgsConstructor
public class ServiceInstance {

    @Id
    private UUID instanceId;

    @Column(nullable = false,length = 100)
    private String serviceName;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Scheme scheme;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(nullable = false, length = 255)
    private String host;

    @Column(nullable = false)
    private Integer port;

    private Instant registeredAt;

    private Instant lastHeartbeat;

    @PrePersist
    void onCreate(){
        Instant instant = Instant.now();
        registeredAt = instant;
        lastHeartbeat = instant;
    }
}
