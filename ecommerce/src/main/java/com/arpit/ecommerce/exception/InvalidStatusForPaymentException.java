package com.arpit.ecommerce.exception;

public class InvalidStatusForPaymentException extends RuntimeException{
    public InvalidStatusForPaymentException(String msg){
        super(msg);
    }
}
