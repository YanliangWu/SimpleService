package com.example.simpleservice.dao;

import com.example.simpleservice.model.record.DomainRecord;

import java.util.List;

public interface DomainDao {

     void insertDomainRecord(String domain, List<String> ips);

     List<DomainRecord> findByDomain(String domain);

}