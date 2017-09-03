package com.maxdidato.coinchange;

public class InsufficientCoinage extends Exception {

    public InsufficientCoinage(String msg){
        super(msg);
    }
}
