package com.arpit.ecommerce.exception;

public class InvalidOrderStatusException extends RuntimeException{
    public  InvalidOrderStatusException (String message){
        super(message );
    }
}
