package com.example.libmanagement.dto;

import lombok.Data;

import java.util.Date;
@Data
public class CheckInResponse {
    private long loanId;
    private String isbn;
    private String title;
    private String borrowerId;
    private String borrowerName;
    private Date borrowedDate;
    private Date dueDate;
    private Date checkInDate;

}
