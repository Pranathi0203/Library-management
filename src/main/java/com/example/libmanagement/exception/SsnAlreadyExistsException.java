package com.example.libmanagement.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class SsnAlreadyExistsException extends Exception{
    public SsnAlreadyExistsException(String exp){
        super(exp);
    }
}
