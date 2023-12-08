package com.example.libmanagement.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class BooksCheckOutCapacityReached extends Exception{
    public BooksCheckOutCapacityReached(String exp){
        super(exp);
    }

}
