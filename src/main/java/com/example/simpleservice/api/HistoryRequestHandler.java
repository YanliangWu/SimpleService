package com.example.simpleservice.api;

import com.example.simpleservice.dao.HistoryDao;
import com.example.simpleservice.model.history.BadHistoryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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
    public Object history() {
        try {
            return historyDao.readLatestQueryHistory(DEFAULT_LIMIT);
        } catch (Exception e) {
            String errMsg = "Error encountered during domain lookup, exception %s".formatted(e);
            return new BadHistoryResponse(errMsg);
        }
    }
}