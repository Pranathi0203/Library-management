package com.example.libmanagement.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class BorrowerNotExistsException extends Exception{
    public BorrowerNotExistsException(String exp){
        super(exp);
    }
}
