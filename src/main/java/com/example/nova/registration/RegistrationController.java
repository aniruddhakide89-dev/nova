package com.example.nova.registration;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/instances")
public class RegistrationController {

    private final RegistrationService registrationService;

    @PostMapping
    public ResponseEntity<RegistrationResponseDTO> register(@Valid @RequestBody RegistrationRequestDTO dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(registrationService.registerServiceInstance(dto));
    }

    @DeleteMapping("/{instanceId}")
    public ResponseEntity<Void> deregister(@PathVariable UUID instanceId){
        registrationService.deregisterServiceInstance(instanceId);
        return ResponseEntity.noContent().build();
    }

}
