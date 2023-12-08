package com.example.libmanagement.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="borrower")
public class Borrower {
    @Id
    private String cardId;
    private String ssn;
    private String fname;
    private String lname;
    private String address;
    private String city;
    private String state;
    private String phone;
}
