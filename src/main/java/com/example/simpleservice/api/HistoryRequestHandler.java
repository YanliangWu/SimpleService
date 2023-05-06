package com.example.simpleservice.api;

import com.example.simpleservice.dao.HistoryDao;
import com.example.simpleservice.model.history.BadHistoryResponse;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@OpenAPIDefinition(info = @Info(title = "My API", version = "1.0", description = "API documentation for My API"))
@Tag(name = "My API", description = "My API description")
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
    @Operation(summary = "Say hello", description = "Returns a greeting message")
    public Object history() {
        try {
            return historyDao.readLatestQueryHistory(DEFAULT_LIMIT);
        } catch (Exception e) {
            String errMsg = "Error encountered during domain lookup, exception %s".formatted(e);
            return new BadHistoryResponse(errMsg);
        }
    }
}