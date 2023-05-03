package com.example.simpleservice.model;

import java.util.List;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "lookups")
@Data
public class LookupResult {

    @Id
    private String id;

    private String domain;

    private List<String> addresses;

    private long createdAt;

}