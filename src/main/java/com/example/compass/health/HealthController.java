package com.example.compass.health;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/health")
@RequiredArgsConstructor
public class HealthController {

    private final HealthService healthService;

    @GetMapping("/services/{serviceName}")
    public ResponseEntity<List<HealthResponseDTO>> getServiceHealth(
            @PathVariable String serviceName) {

        return ResponseEntity.ok(
                healthService.getServiceHealth(serviceName)
        );
    }

    @GetMapping("/instances/{instanceId}")
    public ResponseEntity<HealthResponseDTO> getInstanceHealth(
            @PathVariable UUID instanceId) {

        return ResponseEntity.ok(
                healthService.getInstanceHealthById(instanceId)
        );
    }
}