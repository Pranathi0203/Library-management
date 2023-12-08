package com.example.libmanagement.dto;

import lombok.Data;

import java.util.List;

@Data
public class SearchResponse {
    private Long authorId;
    private String authorNames;
    private String isbn;
    private String title;
    private boolean isUnavailable;

}

