package com.example.demo.validation;

import com.example.demo.validation.CheckCase;
import com.example.demo.validation.CheckCaseEnum;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class LicensePlateValidator implements ConstraintValidator<CheckCase, String> {
// @Checkcase(...)
    //String someData;
    private CheckCaseEnum caseMode;
    private int _myValue;

    @Override
    public void initialize(CheckCase constraintAnnotation) {
        this.caseMode = constraintAnnotation.value();
        this._myValue = constraintAnnotation.myTestValue();
    }

    @Override
    public boolean isValid(String object, ConstraintValidatorContext constraintContext) {
        if ( object == null ) {
            return true;
        }
        boolean isValid;
        if ( caseMode == CheckCaseEnum.Upper && this._myValue == 0) {
            isValid = object.equals( object.toUpperCase() );
        }
        else {
            isValid = object.equals( object.toLowerCase() );
        }

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
