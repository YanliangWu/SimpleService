package com.example.simpleservice.model.history;

import com.example.simpleservice.model.lookup.GoodLookupResponse;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class GoodHistoryResponse extends HistoryResponse {
    @JsonUnwrapped
    List<GoodLookupResponse> records;
}
