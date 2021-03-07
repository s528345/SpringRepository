package com.example.demo.validation.ApiValidationHandler;

import com.example.demo.controller.rest.RestControllerDemo;
import com.example.demo.testInheritance.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Set;

@Service
public class ApiValidationHandler {

    // public static final ApiValidationHandler SHARED = new ApiValidationHandler();

    // private ApiValidationHandler(){}

    @Autowired
    static Validator validator1;

    // generic method configuration for validating viewmodels (api -- subparse objects require
    // manual instigation).  This is due to a REST response retaining 1:m viewmodels and
    // JSON data, not form data, inability to configure primitive mappings
    // (ie: int myNum --> { "myNum" : 3 }   will always fail.
    private enum BindingErrorResponseType { JSON, GENERIC_STRING }

    public static <T extends RestControllerDemo.ApiViewModel> @NotNull Optional<String>
    getApiBindingError(@NotNull final T viewModel, @NotNull final Validator validator) throws JSONException {
        System.out.print("service check: ");
        System.out.println(validator1 == null);
        final String response;


//        System.out.println("manual validation commencing");
        Set<ConstraintViolation<T>> violationSet = validator.validate(viewModel);


        if(!violationSet.isEmpty()){

            if(violationSet.size() != 1)
                throw new RuntimeException("Project Error: size of violation set should only be one." +
                        "Does the view model retain only one validation constraint tag?");

            // small storage optimization : size set to one
            final ArrayList<ConstraintViolation<T>> violation = new ArrayList<ConstraintViolation<T>>(1);

            // iterates over violations (should always be 1 only) and set json array
            // converting set to array loses the type argument integrity
            violation.addAll(violationSet);

            // checks if responseArray is generic or not
            // see live project imp for determination
            // assume here that it is valid JSONArray string
            final BindingErrorResponseType type = 1 == 1 ?
                    BindingErrorResponseType.JSON :
                    BindingErrorResponseType.GENERIC_STRING;


            response = type == BindingErrorResponseType.JSON ?
                    new JSONArray(violation.get(0).getMessage()).toString() :
                    violation.get(0).getMessage();

            // configures error response
            return Optional.of(response);

        }

        // no error uncovered in validation -- empty optional
        return Optional.empty();

    }
}
