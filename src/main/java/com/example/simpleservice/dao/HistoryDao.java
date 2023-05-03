package com.example.simpleservice.dao;

import com.example.simpleservice.model.lookup.GoodLookupResponse;
import com.example.simpleservice.model.lookup.LookupResponse;
import com.example.simpleservice.model.record.DomainRecord;
import com.example.simpleservice.model.record.HistoryRecord;

import java.util.List;

public interface HistoryDao {

     void insertHistoryRecord(HistoryRecord record);

     List<GoodLookupResponse> readLatestQueryHistory(int limit);

}