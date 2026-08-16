package com.example.compass.dashboard;

import com.example.compass.core.Status;
import com.example.compass.discovery.DiscoveryDTO;
import com.example.compass.discovery.DiscoveryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final DiscoveryService discoveryService;

    public DashboardResponseDTO getDashBoard(){
        int upInstances = 0;
        int downInstances = 0;
        Set<String> serviceNames = new HashSet<>();
        List<DiscoveryDTO> discoveryDTOList = discoveryService.getALlInstances();
        for (DiscoveryDTO dto : discoveryDTOList){

            serviceNames.add(dto.getServiceName());
            if(dto.getStatus() == Status.UP){
                upInstances++;
            }
            else{
                downInstances++;
            }
        }
        return DashboardResponseDTO.builder()
                .totalService(serviceNames.size())
                .upInstances(upInstances)
                .downInstances(downInstances)
                .totalInstances(discoveryDTOList.size())
                .serviceInstances(discoveryDTOList)
                .build();
    }

}
