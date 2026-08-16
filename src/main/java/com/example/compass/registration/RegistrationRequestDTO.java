package com.example.compass.registration;

import com.example.compass.core.Scheme;
import lombok.Data;

@Data
public class RegistrationRequestDTO {
    private String serviceName;
    private Scheme scheme;
    private String host;
    private Integer port;
}
