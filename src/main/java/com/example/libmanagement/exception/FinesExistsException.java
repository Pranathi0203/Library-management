package com.example.libmanagement.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class FinesExistsException extends Exception{
    public FinesExistsException(String exp){
        super(exp);
    }
}
