package com.example.nova.discovery;
import com.example.nova.core.Scheme;
import com.example.nova.core.Status;
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
