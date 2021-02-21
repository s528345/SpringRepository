package com.example.demo;

import com.example.demo.controller.repository.DataAccessConversion;

public class DataAccessConversionException extends RuntimeException{
   public DataAccessConversionException(String message){
        super(message);
    }
}
