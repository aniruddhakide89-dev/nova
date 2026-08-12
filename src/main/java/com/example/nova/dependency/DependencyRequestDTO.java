package com.example.nova.dependency;

import lombok.Data;

@Data
public class DependencyRequestDTO {
    private String sourceService;
    private String targetService;
}
