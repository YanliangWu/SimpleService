package com.example.simpleservice.model;

public class IpAddress {
    public String ip;

    public IpAddress(String ip) {
        this.ip = ip;
    }

    public static IpAddress fromString(String ip) {
        return new IpAddress(ip);
    }

}
