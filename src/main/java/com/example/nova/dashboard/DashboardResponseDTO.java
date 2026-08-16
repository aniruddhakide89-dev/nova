package com.example.nova.dashboard;

import com.example.nova.discovery.DiscoveryDTO;
import jakarta.persistence.criteria.CriteriaBuilder;
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
