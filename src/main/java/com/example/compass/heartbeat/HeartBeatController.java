package com.example.compass.heartbeat;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/instances")
public class HeartBeatController {

    private final HeartBeatService heartBeatService;

    @PostMapping("/{instanceId}/heartbeat")
    public ResponseEntity<Void> heartbeat(@PathVariable UUID instanceId){
        heartBeatService.heartbeat(instanceId);
        return ResponseEntity.noContent().build();
    }

}
