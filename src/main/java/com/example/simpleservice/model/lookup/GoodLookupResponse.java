package com.example.simpleservice.model.lookup;


import com.example.simpleservice.model.IpAddress;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper=false)
public class GoodLookupResponse extends LookupResponse {

    private List<IpAddress> addresses;
    @JsonProperty("client_ip")
    private String clientIp;
    @JsonProperty("create_at")
    private long createAt;
    private String domain;

}