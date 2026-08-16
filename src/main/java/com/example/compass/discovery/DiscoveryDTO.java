package com.example.compass.discovery;
import com.example.compass.core.Scheme;
import com.example.compass.core.Status;
import lombok.Data;

import java.util.UUID;

@Data
public class DiscoveryDTO {
    private UUID instanceId;
    private String serviceName;
    private Scheme scheme;
    private Status status;
    private String host;
    private Integer port;
}
