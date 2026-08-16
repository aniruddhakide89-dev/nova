package com.example.compass.dependency;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "service_discovery",uniqueConstraints = {
        @UniqueConstraint(
                columnNames = {"source_service","target_service"}
        )
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServiceDependency {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "source_service", nullable = false)
    private String sourceService;

    @Column(name = "target_service",nullable = false)
    private String targetService;

    private Instant firstSeen;
    private Instant lastSeen;

}
