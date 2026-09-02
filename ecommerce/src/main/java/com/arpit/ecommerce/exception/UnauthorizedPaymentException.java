package com.arpit.ecommerce.exception;

public class UnauthorizedPaymentException extends RuntimeException{
    public UnauthorizedPaymentException (String msg){
        super(msg);}
}