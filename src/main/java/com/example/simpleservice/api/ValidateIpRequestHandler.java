package com.example.simpleservice.api;

import com.example.simpleservice.model.validate.BadValidationResponse;
import com.example.simpleservice.model.validate.GoodValidationResponse;
import com.example.simpleservice.model.validate.IpValidationRequest;
import com.example.simpleservice.model.validate.ValidationResponse;
import com.example.simpleservice.util.IpUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tools")
public class ValidateIpRequestHandler {

    @PostMapping("/validate")
    public ResponseEntity<ValidationResponse> validateIpAddress(@RequestBody IpValidationRequest request) {
        try {
            if (request.getIp() == null || request.getIp().isEmpty()) {
                return ResponseEntity.badRequest().body(new BadValidationResponse("IP address is required"));
            }
            return ResponseEntity.ok(new GoodValidationResponse(IpUtil.isIpv4Address(request.getIp())));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new BadValidationResponse("Exception caught: " + e.getMessage()));
        }
    }

}