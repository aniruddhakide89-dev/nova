package com.example.compass.dependency;

import lombok.Data;

@Data
public class DependencyRequestDTO {
    private String sourceService;
    private String targetService;
}
