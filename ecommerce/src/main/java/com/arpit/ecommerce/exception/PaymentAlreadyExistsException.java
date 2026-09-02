package com.arpit.ecommerce.exception;

public class PaymentAlreadyExistsException extends RuntimeException{
    public PaymentAlreadyExistsException(String msg){
        super(msg);
    }
}
