package com.example.simpleservice.model.history;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@Builder
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class BadHistoryResponse extends HistoryResponse {
    String message;
}
