package com.example.nova.registration;

import com.example.nova.core.Scheme;
import com.example.nova.core.Status;
import lombok.Data;

@Data
public class RegistrationRequestDTO {
    private String serviceName;
    private Scheme scheme;
    private String host;
    private Integer port;
}
