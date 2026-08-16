package com.example.compass.dependency;

import com.example.compass.core.Status;
import lombok.Data;

@Data
public class GraphNodeDTO {
    private String serviceName;
    private Status status;
}
