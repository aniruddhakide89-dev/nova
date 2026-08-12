package com.example.nova.dependency;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ServiceDependencyService {

    private final ServiceDependencyRepository serviceDependencyRepository;
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

}
