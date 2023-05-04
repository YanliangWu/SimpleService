package com.example.simpleservice.model.lookup;


import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Builder
@EqualsAndHashCode(callSuper = false)
public class BadLookupResponse extends LookupResponse {
    private String message;
}