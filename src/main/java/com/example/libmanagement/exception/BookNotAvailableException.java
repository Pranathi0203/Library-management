package com.example.libmanagement.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class BookNotAvailableException extends Exception{
    public BookNotAvailableException(String exp){
        super(exp);
    }

}
