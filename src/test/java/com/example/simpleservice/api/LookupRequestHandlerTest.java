package com.example.simpleservice.api;

import com.example.simpleservice.dao.DomainDao;
import com.example.simpleservice.dao.HistoryDao;
import com.example.simpleservice.model.record.DomainRecord;
import com.example.simpleservice.model.record.HistoryRecord;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@WebMvcTest(LookupRequestHandler.class)
public class LookupRequestHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    @Qualifier("DomainDao")
    private DomainDao domainDao;

    @MockBean
    @Qualifier("HistoryDao")
    private HistoryDao historyDao;

    @Mock
    private HttpServletRequest request;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testLookupEndpointWithValidResult() throws Exception {
        String domainName = "example.com";
        String ipAddress = "192.0.2.1";
        List<String> ipAddresses = Collections.singletonList(ipAddress);
        List<DomainRecord> domainRecords = Collections.singletonList(new DomainRecord(domainName, ipAddresses, 0));

        when(domainDao.findByDomain(anyString())).thenReturn(domainRecords);
        when(request.getRemoteAddr()).thenReturn(ipAddress);

        mockMvc.perform(MockMvcRequestBuilders.get("/tools/lookup")
                        .param("domain", domainName)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.domain").value(domainName))
                .andExpect(MockMvcResultMatchers.jsonPath("$.addresses[0].ip").value(ipAddress));

        verify(historyDao, times(1)).insertHistoryRecord(any(HistoryRecord.class));
    }

    @Test
    public void testLookupEndpointWithNoResultFound() throws Exception {
        String domainName = "example.com";
        List<DomainRecord> domainRecords = Collections.emptyList();

        when(domainDao.findByDomain(anyString())).thenReturn(domainRecords);

        mockMvc.perform(MockMvcRequestBuilders.get("/tools/lookup")
                        .param("domain", domainName)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Domain example.com not found in database"));

        verifyNoInteractions(historyDao);
    }

    @Test
    public void testLookupEndpointWithException() throws Exception {
        String domainName = "example.com";

        when(domainDao.findByDomain(anyString())).thenThrow(new RuntimeException("Test exception message"));

        mockMvc.perform(MockMvcRequestBuilders.get("/tools/lookup")
                        .param("domain", domainName)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Error encountered during domain lookup, exception java.lang.RuntimeException: Test exception message"));

        verifyNoInteractions(historyDao);
    }
}