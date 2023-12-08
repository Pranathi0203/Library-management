package com.example.libmanagement.dto;

import lombok.Data;

import java.util.List;

@Data
public class CheckOutBooksRequest {
    private List<String> isbns;
    private String cardId;
}
