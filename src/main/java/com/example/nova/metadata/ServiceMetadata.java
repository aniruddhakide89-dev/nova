package com.example.nova.metadata;

import com.example.nova.core.ServiceInstance;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Map;
import java.util.UUID;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "service_metadata")
public class ServiceMetadata {

    @Id
    private UUID instanceId;

    @MapsId
    @OneToOne
    @JoinColumn(name = "instance_id")
    private ServiceInstance serviceInstance;

    private String version;
    private String environment;
    private String region;
    private String zone;
    private String team;
    private String framework;
    private String runtime;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private Map<String, String> customMetadata;

}
