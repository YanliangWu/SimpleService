package com.example.simpleservice.model.history;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
@AllArgsConstructor
public class BadHistoryResponse extends HistoryResponse {
    String message;
}
