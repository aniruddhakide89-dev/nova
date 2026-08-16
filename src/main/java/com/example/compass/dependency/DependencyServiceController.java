package com.example.compass.dependency;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/dependencies")
public class DependencyServiceController {

    private final ServiceDependencyService serviceDependencyService;

    @PostMapping
    public ResponseEntity<DependencyResponseDTO> addDependency(
            @Valid @RequestBody DependencyRequestDTO dto) {

        return ResponseEntity.ok(
                serviceDependencyService.addDependency(
                        dto.getSourceService(),
                        dto.getTargetService()
                )
        );
    }

    @GetMapping("/source/{sourceService}")
    public ResponseEntity<List<DependencyResponseDTO>> getBySource(
            @PathVariable String sourceService) {

        return ResponseEntity.ok(
                serviceDependencyService.getDependencyBySource(sourceService)
        );
    }

    @GetMapping("/target/{targetService}")
    public ResponseEntity<List<DependencyResponseDTO>> getByTarget(
            @PathVariable String targetService) {

        return ResponseEntity.ok(
                serviceDependencyService.getDependencyByTarget(targetService)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDependency(
            @PathVariable UUID id) {

        serviceDependencyService.deleteDependencyById(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/graph")
    public ResponseEntity<GraphDependencyResponseDTO> getDependencyGraph(){
        return ResponseEntity.ok(serviceDependencyService.getDependencyGraph());
    }

    @GetMapping("/{targetService}/impact")
    public ResponseEntity<List<CascadingImpactedServiceDTO>> getCascadingImpact(@PathVariable String targetService){
        return ResponseEntity.ok(serviceDependencyService.getCascadingImpactedServices(targetService));
    }

}
