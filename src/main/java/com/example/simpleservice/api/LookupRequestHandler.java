package com.example.simpleservice.api;

import com.example.simpleservice.dao.DomainDao;
import com.example.simpleservice.dao.HistoryDao;
import com.example.simpleservice.model.IpAddress;
import com.example.simpleservice.model.lookup.BadLookupResponse;
import com.example.simpleservice.model.lookup.GoodLookupResponse;
import com.example.simpleservice.model.lookup.LookupResponse;
import com.example.simpleservice.model.record.DomainRecord;
import com.example.simpleservice.model.record.HistoryRecord;
import com.example.simpleservice.util.IpUtil;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/tools")
@OpenAPIDefinition(info = @Info(title = "My API", version = "1.0", description = "API documentation for My API"))

public class LookupRequestHandler {
    private final DomainDao domainDao;
    private final HistoryDao historyDao;

    @Autowired
    public LookupRequestHandler(@Qualifier("DomainDao") DomainDao domainDao,
                                @Qualifier("HistoryDao") HistoryDao historyDao) {
        this.domainDao = domainDao;
        this.historyDao = historyDao;
    }

    @GetMapping("/lookup")
    @ResponseBody
    public LookupResponse lookup(@RequestParam String domain, HttpServletRequest request) {

        try {
            List<String> ipv4Addresses;
            List<DomainRecord> results;
            results = domainDao.findByDomain(domain);
            String callerIp = request.getRemoteAddr();

            if (!results.isEmpty()) {
                // Get the DomainRecord that has the latest created_at timestamp
                DomainRecord latestRecord = results.stream().max((a, b) -> (int) (a.getCreateAt() - b.getCreateAt())).get();
                ipv4Addresses = new ArrayList<>(latestRecord.getAddresses().stream().filter(IpUtil::isIpv4Address).toList());
            } else {
                String errMsg = "Domain %s not found in database".formatted(domain);
                return BadLookupResponse.builder().message(errMsg).build();
            }
            GoodLookupResponse res = GoodLookupResponse
                    .builder()
                    .clientIp(callerIp)
                    .domain(domain)
                    .createAt(System.currentTimeMillis())
                    .addresses(ipv4Addresses.stream().map(IpAddress::new).toList())
                    .build();

            historyDao.insertHistoryRecord(new HistoryRecord(Instant.now().getEpochSecond(), res));

            return res;

        } catch (Exception e) {
            String errMsg = "Error encountered during domain lookup, exception %s".formatted(e);
            return BadLookupResponse
                    .builder()
                    .message(errMsg)
                    .build();
        }
    }
}