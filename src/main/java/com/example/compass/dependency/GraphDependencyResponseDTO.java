package com.example.compass.dependency;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class GraphDependencyResponseDTO {
    private List<GraphNodeDTO> nodes;
    private List<GraphEdgeDTO> edges;
}
