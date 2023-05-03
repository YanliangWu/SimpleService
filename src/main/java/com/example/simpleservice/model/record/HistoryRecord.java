package com.example.simpleservice.model.record;

import com.example.simpleservice.model.lookup.GoodLookupResponse;
import com.example.simpleservice.model.lookup.LookupResponse;
import com.fasterxml.jackson.annotation.JsonProperty;

public record HistoryRecord (
        @JsonProperty("query_time")
        long queryTime,
        GoodLookupResponse record
        ){
}
