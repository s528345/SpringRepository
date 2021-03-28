package com.example.demo.testInheritance;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


public class A {

    public String fName;

    public String getName(){
        return fName;
    }

    public A(String fName){
        this.fName = fName;
    }
}
