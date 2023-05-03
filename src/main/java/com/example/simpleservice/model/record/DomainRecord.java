package com.example.simpleservice.model.record;

import com.example.simpleservice.model.LookupResult;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record DomainRecord(
        String domain,
        List<String> addresses,
        @JsonProperty("create_at")
        long createAt
) {
    public static DomainRecord fromLookupResult(LookupResult lookupResult) {
        return new DomainRecord(
                lookupResult.getDomain(),
                lookupResult.getAddresses(),
                lookupResult.getCreatedAt()
        );
    }

    public String getDomain() {
        return domain;
    }

    public List<String> getAddresses() {
        return addresses;
    }

    public long getCreateAt() {
        return createAt;
    }



}
