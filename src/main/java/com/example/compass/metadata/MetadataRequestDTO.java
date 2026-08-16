package com.example.compass.metadata;

import lombok.Data;
import java.util.Map;

@Data
public class MetadataRequestDTO {
    private String version;
    private String environment;
    private String region;
    private String zone;
    private String team;
    private String framework;
    private String runtime;
    private Map<String, String> customMetadata;
}
