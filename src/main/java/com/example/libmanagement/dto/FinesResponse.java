package com.example.libmanagement.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class FinesResponse{
    private long loanId;
    private String borrowerId;
    private String borrowerName;
//    private String isbn;
//    private String title;
//    private Date borrowedDate;
//    private Date dueDate;
//    private Date checkInDate;
    private double totalFine;


}
