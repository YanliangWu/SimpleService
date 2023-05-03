package com.example.simpleservice.config;

import com.example.simpleservice.dao.DomainDao;
import com.example.simpleservice.dao.HistoryDao;
import com.example.simpleservice.dao.MongoDomainDao;
import com.example.simpleservice.dao.MongoHistoryDao;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;

@Configuration
public class ApplicationConfig {


    @Value("${spring.data.mongodb.uri}")
    private String mongoUri;

    @Value("${spring.data.mongodb.database}")
    private String database;

    @Value("${spring.data.mongodb.collection.domain}")
    private String domainTable;

    @Value("${spring.data.mongodb.collection.history}")
    private String historyTable;

    @Bean
    public MongoClient mongoClient() {
        return MongoClients.create(mongoUri);
    }

    @Bean
    public MongoTemplate mongoTemplate() {
        return new MongoTemplate(new SimpleMongoClientDatabaseFactory(mongoClient(), database));
    }
    @Bean
    @Qualifier("DomainDao")
    public DomainDao domainDao() {
        return new MongoDomainDao(mongoTemplate(), domainTable);
    }
    @Bean
    @Qualifier("HistoryDao")
    public HistoryDao historyDao() {
        return new MongoHistoryDao(mongoTemplate(), historyTable);
    }

}