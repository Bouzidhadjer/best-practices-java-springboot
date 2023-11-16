package com.hadjerbouzid.springbootbestpractices.exception;

public class ProductNotFoundException  extends RuntimeException {

    public  ProductNotFoundException(String message){
         super(message);
    }
}
