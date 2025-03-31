package com.inspire12.likelionbackend.module.core.properties.service;

import com.inspire12.likelionbackend.module.core.properties.config.CustomEnvProperties;
import com.inspire12.likelionbackend.module.core.properties.config.ServerPortProperties;
import org.apache.catalina.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
public class EnvApplicationPlaygroundService {

    private final Logger log = LoggerFactory.getLogger(this.getClass().getName());

    private final CustomEnvProperties customEnvProperties;
    private final ServerPortProperties serverPortProperties;


    public EnvApplicationPlaygroundService(CustomEnvProperties customEnvProperties, ServerPortProperties serverPortProperties) {
        this.customEnvProperties = customEnvProperties;
        this.serverPortProperties = serverPortProperties;
    }

    public String getCustomValue() {
        // TODO
        return customEnvProperties.getEnv();
    }


    public String getServerPort() {
        // TODO
        return serverPortProperties.getPort();
    }

}
