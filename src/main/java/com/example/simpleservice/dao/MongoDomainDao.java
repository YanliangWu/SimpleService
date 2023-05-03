package com.example.simpleservice.dao;

import java.util.List;

import com.example.simpleservice.model.record.DomainRecord;
import com.example.simpleservice.model.LookupResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.time.Instant;

@Repository
public class MongoDomainDao implements DomainDao {


    private final MongoTemplate mongoTemplate;

    private final String domainTable;

    public MongoDomainDao(MongoTemplate mongoTemplate,
                          @Value("${spring.data.mongodb.collection.domain}") String domainTable) {
        this.mongoTemplate = mongoTemplate;
        this.domainTable = domainTable;
    }

    @Override
    public void insertDomainRecord(String domain, List<String> ips) {
        DomainRecord domainRecord = new DomainRecord(domain, ips, Instant.now().getEpochSecond());
        mongoTemplate.insert(domainRecord);
    }

    @Override
    public List<DomainRecord> findByDomain(String domain) {
        return mongoTemplate.find(
                Query.query(Criteria.where("domain").is(domain)),
                LookupResult.class,
                domainTable).stream().map(DomainRecord::fromLookupResult).toList();
    }
}