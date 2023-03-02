package com.ali.exception;

import lombok.Getter;

@Getter
public class UserMicroServiceException extends  RuntimeException{

    private final ErrorType errorType;

    public UserMicroServiceException(ErrorType errorType){
        super(errorType.getMessage());
        this.errorType=errorType;
    }

    public UserMicroServiceException(ErrorType errorType , String message){
        super(message);
        this.errorType=errorType;

    }

}
