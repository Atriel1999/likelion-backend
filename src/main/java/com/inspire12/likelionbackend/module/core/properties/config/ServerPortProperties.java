package com.inspire12.likelionbackend.module.core.properties.config;


import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class ServerPortProperties {
    @Value("${server.port}")
    private String port;
}
