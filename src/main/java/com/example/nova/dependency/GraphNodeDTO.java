package com.example.nova.dependency;

import com.example.nova.core.Status;
import lombok.Data;

@Data
public class GraphNodeDTO {
    private String serviceName;
    private Status status;
}
