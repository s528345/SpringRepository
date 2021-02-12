package com.example.demo.validation;

import com.example.demo.LicensePlate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class LicensePlateClassValidator implements ConstraintValidator<LicensePlateClassValidatorInterface, LicensePlate> {

    private int yearsOwned ;
    private String name;

    @Override
    public void initialize(LicensePlateClassValidatorInterface constraintAnnotation) {
        this.yearsOwned = constraintAnnotation.value();
        this.name = constraintAnnotation.myTestValue();
    }

    @Override
    public boolean isValid(LicensePlate object, ConstraintValidatorContext constraintContext) {
        if ( object == null ) {
            return true;
        }
        boolean isValid = false;

        if(object.getYearsOwned() != this.yearsOwned && !object.getOwnerName().equals(this.name))
            isValid = true;

        if ( !isValid ) {
            constraintContext.disableDefaultConstraintViolation();
            constraintContext.buildConstraintViolationWithTemplate(
                    "{org.hibernate.validator.referenceguide.chapter03." +
                            "constraintvalidatorcontext.CheckCase.message}" +
                            "you've done fucked up boi"
            )
                    .addConstraintViolation();
        }

        return isValid;
    }
}

