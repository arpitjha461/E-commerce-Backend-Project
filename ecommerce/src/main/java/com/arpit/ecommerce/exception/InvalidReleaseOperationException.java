package com.arpit.ecommerce.exception;

public class InvalidReleaseOperationException extends RuntimeException{
    public InvalidReleaseOperationException(String message){
        super(message);
    }
}
