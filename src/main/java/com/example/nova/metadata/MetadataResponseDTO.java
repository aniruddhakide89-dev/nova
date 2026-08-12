package com.example.nova.metadata;

import lombok.Data;
import java.util.Map;
import java.util.UUID;

@Data
public class MetadataResponseDTO {

    private UUID instanceId;
    private String version;
    private String environment;
    private String region;
    private String zone;
    private String team;
    private String framework;
    private String runtime;
    private Map<String, String> customMetadata;

}
