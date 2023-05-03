package com.example.simpleservice.api;

import com.example.simpleservice.dao.DomainDao;
import com.example.simpleservice.dao.HistoryDao;
import com.example.simpleservice.model.IpAddress;
import com.example.simpleservice.model.history.BadHistoryResponse;
import com.example.simpleservice.model.history.GoodHistoryResponse;
import com.example.simpleservice.model.history.HistoryResponse;

import com.example.simpleservice.model.lookup.GoodLookupResponse;
import com.example.simpleservice.model.lookup.LookupResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
public class HistoryRequestHandler {
    private final HistoryDao historyDao;
    private final int DEFAULT_LIMIT = 20;
    @Autowired
    public HistoryRequestHandler(@Qualifier("HistoryDao") HistoryDao domainDao) {
        this.historyDao = domainDao;
    }

    @GetMapping("/history")
    @ResponseBody
    public HistoryResponse history() {
        try{
            List<GoodLookupResponse> results = historyDao.readLatestQueryHistory(DEFAULT_LIMIT);
            return new GoodHistoryResponse(results);
        } catch (Exception e){
            String errMsg = "Error encountered during domain lookup, exception %s".formatted(e);
            return new BadHistoryResponse(errMsg);
        }
    }
}