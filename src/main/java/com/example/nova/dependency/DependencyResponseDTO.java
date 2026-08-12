package com.example.nova.dependency;

import jakarta.persistence.Column;
import lombok.Data;

import java.time.Instant;

@Data
public class DependencyResponseDTO {

    private String sourceService;
    private String targetService;
    private Instant firstSeen;
    private Instant lastSeen;

}
