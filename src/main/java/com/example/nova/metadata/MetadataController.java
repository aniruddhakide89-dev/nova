package com.example.nova.metadata;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/instances")
public class MetadataController {

    private final MetadataService metadataService;

    @GetMapping("/{instanceId}/metadata")
    public ResponseEntity<MetadataResponseDTO> getMetadata(@PathVariable UUID instanceId){
        return ResponseEntity.ok(metadataService.getMetadataById(instanceId));
    }

    @PostMapping("/{instanceId}/metadata")
    public ResponseEntity<MetadataResponseDTO> registerMetadata(@PathVariable UUID instanceId, @Valid @RequestBody MetadataRequestDTO metadataRequestDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(metadataService.registerMetadata(instanceId, metadataRequestDTO));
    }

    @DeleteMapping("/{instanceId}/metadata")
    public ResponseEntity<Void> deregisterMetadata(@PathVariable UUID instanceId){
        metadataService.deregisterMetadata(instanceId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{instanceId}/metadata")
    public ResponseEntity<MetadataResponseDTO> updateMetadata(@PathVariable UUID instanceId, @Valid @RequestBody MetadataRequestDTO metadataRequestDTO){
        return ResponseEntity.ok(metadataService.updateMetadata(instanceId, metadataRequestDTO));
    }

}
