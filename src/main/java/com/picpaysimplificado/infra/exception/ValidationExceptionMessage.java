package com.picpaysimplificado.infra.exception;

public class ValidationExceptionMessage extends RuntimeException {

    public ValidationExceptionMessage(String message){
        super(message);
    }

}
