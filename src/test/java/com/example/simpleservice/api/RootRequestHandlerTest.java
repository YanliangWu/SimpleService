package com.example.simpleservice.api;

import com.example.simpleservice.model.RootResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.when;

@WebMvcTest(RootRequestHandler.class)
public class RootRequestHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RootRequestHandler rootRequestHandler;

    @Test
    public void testRootEndpoint() throws Exception {
        String appVersion = "1.0.0";
        boolean isRunningInKubernetes = true;
        long epochSeconds = System.currentTimeMillis() / 1000;
        RootResponse expectedResponse = new RootResponse(appVersion, epochSeconds, isRunningInKubernetes);

        when(rootRequestHandler.root()).thenReturn(expectedResponse);

        mockMvc.perform(MockMvcRequestBuilders.get("/")
                        .accept("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.version").value(appVersion))
                .andExpect(MockMvcResultMatchers.jsonPath("$.kubernetes").value(isRunningInKubernetes));
    }
}