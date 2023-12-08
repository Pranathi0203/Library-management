package com.example.libmanagement.dto;

import lombok.Data;

@Data
public class BookAuthorDTO {

    private Long authorId;
    private String authorName;
    private String isbn;
    private String title;
    private boolean isUnavailable;
}
