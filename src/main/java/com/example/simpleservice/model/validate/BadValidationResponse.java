package com.example.simpleservice.model.validate;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Builder
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public class BadValidationResponse extends ValidationResponse {
    private String message;
}