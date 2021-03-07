package com.example.demo.controller.rest;

import com.example.demo.LicensePlate1;
import com.example.demo.testInheritance.B;
import com.example.demo.validation.ApiValidationHandler.ApiValidationHandler;
import com.example.demo.validation.CheckCase;
import com.example.demo.validation.LicensePlateClassValidator;
import com.example.demo.validation.LicensePlateClassValidatorInterface;
import org.javatuples.Pair;
import org.javatuples.Triplet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.example.demo.fakeData;
import com.example.demo.controller.repository.*;

import javax.validation.ConstraintValidatorContext;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping(path = "/rest/api/")
public class RestControllerDemo {

    @RequestMapping(path = "/customHeader", method = RequestMethod.GET,
    consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<fakeData> customHeader(@RequestBody Map<String, String> bodyMap) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Custom-Header", "foo");

        System.out.println(bodyMap.get("name") == null || bodyMap.get("age") == null);

        // inheritance test from static context
        B.printFullName();

        return new ResponseEntity<fakeData>(
                new fakeData(
                        (String)bodyMap.get("name"),
                        (String)bodyMap.get("age")),
                headers,
                HttpStatus.OK);
    }

    @RequestMapping(path = "/customHeader/custom", method = RequestMethod.GET,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Map<String, Object>> customHeaderResponse(@RequestBody Map<String, String> bodyMap)
    throws NumberFormatException{
        HttpHeaders headers = new HttpHeaders();
        headers.add("Custom-Header", "foo");

        System.out.println(bodyMap.get("name") == null || bodyMap.get("age") == null);

        return new ResponseEntity<Map<String, Object>>(
                Map.of(
                        "name", bodyMap.get("name"),
                        "age", bodyMap.get("age"),
                        "number", Integer.parseInt(bodyMap.get("number")),
                        "internalObject", new fakeData("nick", "pierce")
                ),
                headers,
                HttpStatus.OK);
    }

    @Autowired
    private MyTableRepository _myTableRepository;

    @GetMapping(path = "/database", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Map<String, Object>> getDataBaseData(){

        List<MyTable> myTableList = this._myTableRepository.getAllStudentsLike("");

//        for(Object[] objects : myTableList)
//            for(Object object: objects)
//                System.out.println(object);

        return new ResponseEntity<Map<String, Object>>(
                Map.of(
                        "data", myTableList
                ),
                new HttpHeaders(),
                HttpStatus.OK
        );

    }

    public class Class{
        String name;
        String courseLevel;
        int capacity;
        int availability;

        Class(String n, String cl, int  cap, int avail){
            this.name = n;
            this.courseLevel = cl;
            this.capacity = cap;
            this.availability = avail;
        }

        public int getAvailability() {
            return availability;
        }

        public int getCapacity() {
            return capacity;
        }

        public String getCourseLevel() {
            return courseLevel;
        }

        public String getName() {
            return name;
        }

        public void setAvailability(int availability) {
            this.availability = availability;
        }

        public void setCapacity(int capacity) {
            this.capacity = capacity;
        }

        public void setCourseLevel(String courseLevel) {
            this.courseLevel = courseLevel;
        }

        public void setName(String name) {
            this.name = name;
        }


    }

    @RequestMapping(path = "/frontEnd", method = RequestMethod.POST,
    consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> FrontEndREST(@RequestBody String jsonString)
    throws JSONException {

        System.out.println(jsonString);

        JSONObject modelMap = new JSONObject(jsonString);

        if(false)
            return new ResponseEntity<Map<String, Object>>(
                        Map.of("number", 2),
                    new HttpHeaders(),
                    HttpStatus.OK
                    );

        /* request body:
            - name: person name (string)
            - age: person age (int)

            response body:
            -  ifPersonExist: if the person already exist (bool)
            -  classes: list of classes student takes (List<Classes>)
         */

        // pulls student name out of request
        String personName = (String)modelMap.get("name");
        int age = (int)modelMap.get("age");

        Class[] obj = {};

        // validation (dummy): if person name is not equal to "seth" or "austin"
        // age is not less than 18 but not greater than 100
        if(personName.equals("seth") || personName.equals("austin") || age < 18 || age > 100)
            return new ResponseEntity<Map<String, Object>>(
                    Map.of(
                            "ifPersonExist", !(personName.equals("seth") || personName.equals("austin")),
                            "classes", obj

                    ),
                    new HttpHeaders(),
                    HttpStatus.BAD_REQUEST
            );

        Class[] classList = {new Class("prog1", "44101", 30, 21),
        new Class("network fundamentals", "44350", 40, 35)};

        return new ResponseEntity<Map<String, Object>>(
                Map.of(
                        "ifPersonExist", true,
                        "classes", classList
                ),
                new HttpHeaders(),
                HttpStatus.OK
        );

    }

    @Autowired
    private DateTableRepository _dateTableRepository;

    @GetMapping(path = "/database/date", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Map<String, Object>> getDateDataBaseData(){

        List<DateTable> myTableList = this._dateTableRepository.getAll();

//        for(Object[] objects : myTableList)
//            for(Object object: objects)
//                System.out.println(object);

        return new ResponseEntity<Map<String, Object>>(
                Map.of(
                        "data", myTableList
                ),
                new HttpHeaders(),
                HttpStatus.OK
        );

    }

    private static final String OPTIONAL_NULL_ERROR = "Error: cannot accessed configured error response -- contact IT support";

    @Autowired
    private Validator validator;

    @GetMapping(path = "/testValidator", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getTestValidator(@RequestBody Map<String, Object> map) throws JSONException {


        System.out.println(map.containsKey("yearsOwned"));
        System.out.println(map.containsKey("licensePlate"));
        System.out.println(map.containsKey("ownerName"));

        final LicensePlate1 plate1 = new LicensePlate1(
                (String)map.get("ownerName"),
                (String)map.get("licensePlate"),
                (int)map.get("yearsOwned")
        );

        Optional<String> validationResponse =
                ApiValidationHandler.getApiBindingError(plate1, validator);

        // NOTE: error type will never be a generic error in this demo
        if(validationResponse.isPresent()){


//
//            final String response;
//
//            if(errorResponse.getValue2() == BindingErrorResponseType.JSON)
//                response = errorResponse.getValue0().isPresent() ?
//                        errorResponse.getValue0().get().toString() : OPTIONAL_NULL_ERROR;
//            else
//                response = errorResponse.getValue1().isPresent() ?
//                        errorResponse.getValue1().get() : OPTIONAL_NULL_ERROR;


            return new ResponseEntity<String>(
                     validationResponse.orElseThrow(() -> new RuntimeException("Error accessing validation-binding response. Project compromised." +
                             "Please contact IT Support.")),
                    new HttpHeaders(),
                    HttpStatus.OK
            );
        }

        // do other important business logic

        // empty json array (demo purpose)
        return new ResponseEntity<String>(
                new JSONArray().toString(),
                new HttpHeaders(),
                HttpStatus.OK
        );

    }

    // would hold all api view models
    public interface ApiViewModel{}


}
