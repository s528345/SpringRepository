package com.example.demo.validation;

import java.lang.annotation.Annotation;

public enum CheckCaseEnum { Upper("upper"), Lower("lower");
    private final String message;

    CheckCaseEnum(String message){
        this.message = message;
    }

    public String getValue(){
        return this.message;
    }
}
