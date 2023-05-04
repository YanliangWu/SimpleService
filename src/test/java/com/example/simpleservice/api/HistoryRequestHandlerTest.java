package com.example.simpleservice.api;

import com.example.simpleservice.dao.HistoryDao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
public class HistoryRequestHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    @Qualifier("HistoryDao")
    private HistoryDao historyDao;

    @Test
    public void testHistoryEndpoint() throws Exception {
        when(historyDao.readLatestQueryHistory(anyInt())).thenReturn(Collections.emptyList());

        mockMvc.perform(MockMvcRequestBuilders.get("/history")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testHistoryEndpointWithException() throws Exception {
        String expectedErrMsg = "Test exception message";
        doThrow(new RuntimeException(expectedErrMsg)).when(historyDao).readLatestQueryHistory(anyInt());

        mockMvc.perform(MockMvcRequestBuilders.get("/history")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }
}