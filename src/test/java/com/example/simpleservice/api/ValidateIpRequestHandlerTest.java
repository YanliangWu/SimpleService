package com.example.simpleservice.api;

import com.example.simpleservice.model.validate.BadValidationResponse;
import com.example.simpleservice.util.IpUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(ValidateIpRequestHandler.class)
public class ValidateIpRequestHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IpUtil ipUtil;

    @Test
    public void testValidateIpAddressEndpointWithValidIp() throws Exception {
        String ipAddress = "192.0.2.1";

        mockMvc.perform(MockMvcRequestBuilders.post("/tools/validate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"ip\":\"" + ipAddress + "\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testValidateIpAddressEndpointWithInvalidIp() throws Exception {
        String ipAddress = "invalid-ip-address";

        mockMvc.perform(MockMvcRequestBuilders.post("/tools/validate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"ip\":\"" + ipAddress + "\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testValidateIpAddressEndpointWithMissingIp() throws Exception {
        BadValidationResponse expectedResponse = new BadValidationResponse("IP address is required");

        mockMvc.perform(MockMvcRequestBuilders.post("/tools/validate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"ip\":\"\"}"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

}