package com.example.simpleservice.model;

public record RootResponse(
        String version,
        long date,
        boolean kubernetes
) {
}
