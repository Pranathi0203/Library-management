package com.example.libmanagement.dto;

import lombok.Data;

import java.util.List;

@Data
public class CheckInOrPayFinesRequest {

    private List<Long> loanId;
    private String cardId;
}
