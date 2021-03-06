package com.example.demo.validation;

import com.example.demo.LicensePlate;
import com.example.demo.LicensePlate1;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ManualValidatorClass implements ConstraintValidator<ManualValidatorInterface, LicensePlate1> {

    private int yearsOwned ;
    private String name;

    @Override
    public void initialize(ManualValidatorInterface constraintAnnotation) {
        this.yearsOwned = constraintAnnotation.value();
        this.name = constraintAnnotation.myTestValue();
    }

    @Override
    public boolean isValid(LicensePlate1 object, ConstraintValidatorContext constraintContext) {

        if ( object == null ) {
            return true;
        }

        boolean isValid = false;

        if(object.getYearsOwned() != this.yearsOwned && !object.getOwnerName().equals(this.name))
            isValid = true;

        if ( !isValid ) {
            constraintContext.disableDefaultConstraintViolation();
            constraintContext.buildConstraintViolationWithTemplate(
                    "[ {  \"Oops\" : \"you done messed up my guy\" }  ] "
            )
                    .addConstraintViolation();
        }

        return isValid;
    }
}

