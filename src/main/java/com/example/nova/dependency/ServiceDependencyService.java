package com.example.nova.dependency;

import com.example.nova.core.ServiceInstance;
import com.example.nova.core.ServiceInstanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ServiceDependencyService {

    private final ServiceDependencyRepository serviceDependencyRepository;
    private final ServiceInstanceRepository serviceInstanceRepository;
    private final DependencyServiceMapper dependencyServiceMapper;

    public DependencyResponseDTO addDependency(String sourceService, String targetService) {
        Instant now = Instant.now();
        ServiceDependency serviceDependency = serviceDependencyRepository.findBySourceServiceAndTargetService(sourceService, targetService).orElseGet(() -> ServiceDependency.builder().sourceService(sourceService).targetService(targetService).firstSeen(now).build());
        serviceDependency.setLastSeen(now);
        serviceDependencyRepository.save(serviceDependency);
        return dependencyServiceMapper.toDTO(serviceDependency);
    }

    public List<DependencyResponseDTO> getDependencyBySource(String sourceService){
        return serviceDependencyRepository.findBySourceService(sourceService).stream().map(dependencyServiceMapper :: toDTO).toList();
    }

    public List<DependencyResponseDTO> getDependencyByTarget(String targetService){
        return serviceDependencyRepository.findByTargetService(targetService).stream().map(dependencyServiceMapper :: toDTO).toList();
    }

    public void deleteDependencyById(UUID id){
        serviceDependencyRepository.deleteById(id);
    }

    public GraphDependencyResponseDTO getDependencyGraph(){
        List<GraphNodeDTO> graphNodeDTOList = serviceInstanceRepository.findAll().stream().map(serviceInstance -> {
            GraphNodeDTO graphNodeDTO = new GraphNodeDTO();
            graphNodeDTO.setServiceName(serviceInstance.getServiceName());
            graphNodeDTO.setStatus(serviceInstance.getStatus());
            return graphNodeDTO;
        }).toList();

        List<GraphEdgeDTO> graphEdgeDTOList = serviceDependencyRepository.findAll().stream().map(serviceDependency -> {
            GraphEdgeDTO graphEdgeDTO = new GraphEdgeDTO();
            graphEdgeDTO.setSourceService(serviceDependency.getSourceService());
            graphEdgeDTO.setTargetService(serviceDependency.getTargetService());
            return graphEdgeDTO;
        }).toList();

        return new GraphDependencyResponseDTO(graphNodeDTOList,graphEdgeDTOList);
    }

    public List<CascadingImpactedServiceDTO> getCascadingImpactedServices(String targetService){
        Set<String> impactedServices = new HashSet<>();
        Queue<String> queue = new LinkedList<>();

        queue.add(targetService);

        while(!queue.isEmpty()){
            String currentService = queue.poll();
            List<ServiceDependency> serviceDependencies = serviceDependencyRepository.findByTargetService(currentService);
            for (ServiceDependency serviceDependency : serviceDependencies){
                String dependentService = serviceDependency.getSourceService();
                if(impactedServices.add(dependentService)){
                    queue.add(dependentService);
                }
            }
        }

        return impactedServices.stream().map(CascadingImpactedServiceDTO::new).toList();
    }


}
