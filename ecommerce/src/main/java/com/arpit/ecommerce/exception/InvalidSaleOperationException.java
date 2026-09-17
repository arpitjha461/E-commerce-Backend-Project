package com.arpit.ecommerce.exception;

public class InvalidSaleOperationException extends RuntimeException{
    public InvalidSaleOperationException(String message){
        super(message);
    }
}
