package com.example.simpleservice.dao;

import com.example.simpleservice.model.lookup.GoodLookupResponse;
import com.example.simpleservice.model.lookup.LookupResponse;
import com.example.simpleservice.model.record.DomainRecord;
import com.example.simpleservice.model.record.HistoryRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MongoHistoryDao implements HistoryDao {

    private final MongoTemplate mongoTemplate;

    private final String historyTable;

    public MongoHistoryDao(MongoTemplate mongoTemplate,
                          @Value("${spring.data.mongodb.collection.history}") String historyTable) {
        this.mongoTemplate = mongoTemplate;
        this.historyTable = historyTable;
    }
    @Override
    public void insertHistoryRecord(HistoryRecord record) {
        mongoTemplate.insert(record, historyTable);
    }

    @Override
    public List<GoodLookupResponse> readLatestQueryHistory(int limit) {
        Query query = new Query().with(Sort.by(Sort.Direction.DESC, "query_time")).limit(20);
        var res =  mongoTemplate.find(query, HistoryRecord.class, historyTable);
        return res.stream().map(HistoryRecord::record).toList();
    }
}
