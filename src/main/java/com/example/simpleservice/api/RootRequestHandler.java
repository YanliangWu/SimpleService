package com.example.simpleservice.api;

import com.example.simpleservice.model.RootResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RootRequestHandler {

    @Value("${spring.application.version}")
    private String appVersion;

    @Value("${kubernetes.enabled:false}")
    private boolean isRunningInKubernetes;

    @GetMapping("/")
    public RootResponse root() {
        long epochSeconds = System.currentTimeMillis() / 1000;
        return new RootResponse(appVersion, epochSeconds, isRunningInKubernetes);
    }

}