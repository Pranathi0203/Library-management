package com.example.libmanagement.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="fines")
public class Fines implements Serializable {
//    @Id
//    @OneToOne
//    @JoinColumn(name = "loanId")
//    private BookLoans bookLoans;
    @Id
    @Column(name = "loan_id")
    private Long loanId;


    private Double fineAmt;


    private boolean paid;

    @OneToOne(mappedBy="fines")
    private BookLoans bookLoans;


//    @OneToOne
//    @JoinColumn(name = "loan_id")
//    private BookLoans bookLoans;

//    @OneToOne
//    @MapsId
//    @JoinColumn(name = "loanId")
//    private BookLoans bookLoans;

}
