package com.example.compass.discovery;

import com.example.compass.core.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/instances")
@RequiredArgsConstructor
public class DiscoveryController {

    private final DiscoveryService discoveryService;

    @GetMapping
    public ResponseEntity<List<DiscoveryDTO>> discover(@RequestParam String serviceName , @RequestParam Status status){
        return ResponseEntity.ok(discoveryService.discover(serviceName.toUpperCase(),status));
    }

}
