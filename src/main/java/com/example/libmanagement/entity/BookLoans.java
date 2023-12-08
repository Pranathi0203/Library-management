package com.example.libmanagement.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="book_loans")
public class BookLoans {
    @Id
    private long loanId;

    @ManyToOne
    @JoinColumn(name = "isbn")
    private Book book;

    @ManyToOne
    @JoinColumn(name = "cardId")
    private Borrower borrower;

    private Date dateOut;
    private Date dueDate;
    private Date dateIn;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "loanId")
    private Fines fines;


}
