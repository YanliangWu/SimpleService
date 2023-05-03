package com.example.simpleservice.model.history;

import com.example.simpleservice.model.lookup.GoodLookupResponse;
import com.example.simpleservice.model.lookup.LookupResponse;
import com.example.simpleservice.model.record.DomainRecord;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class GoodHistoryResponse extends HistoryResponse{
    List<GoodLookupResponse> records;
}
