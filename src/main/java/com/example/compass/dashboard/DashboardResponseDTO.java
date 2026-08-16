package com.example.compass.dashboard;

import com.example.compass.discovery.DiscoveryDTO;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class DashboardResponseDTO {
    // Statistics
    private Integer totalService;
    private Integer upInstances;
    private Integer downInstances;
    private Integer totalInstances;

    //ServiceInstances
    List<DiscoveryDTO> serviceInstances;
}
