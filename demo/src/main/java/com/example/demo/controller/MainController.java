package com.example.demo.controller;

import com.example.demo.Car;
import com.example.demo.LicensePlate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.example.demo.fakeData;
import org.springframework.http.MediaType;

import javax.sound.midi.SysexMessage;
import javax.validation.Valid;
import com.example.demo.Car;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Controller
@RequestMapping(path = "/")
public class MainController {
    // this boi key for transactional, custom JPA repo logic.
    @PersistenceContext
    EntityManager entityManager;

    private fakeData _fakeData;

    @GetMapping("/")
    public String index(Map<String, Object> model, ModelMap map){

        System.out.println("test");
        map.addAttribute("test", "value");
        map.addAttribute("test1", null);
        System.out.println("12a".toUpperCase(Locale.ROOT));

        Car car = new Car("m ","l ", 0);
        Optional<Car> carOpt = car.getSelfCar();
        Optional<Car> carOptNull = car.getNullSelfCar(true);

        System.out.println("is carOpt null? "+ carOptNull.isEmpty() + " : value - " + carOpt.orElseGet( () -> {return car;}));
        System.out.println("is carOpt null? "+ carOptNull.isEmpty() + " : value - " +
                carOpt.map((car1 -> {return car1;})).orElseGet( () -> {return car;}));

        if(carOpt.isPresent()){ // long but easy way
            // safe to get
            Car myLocalCar = carOpt.get();
            String output = fakeData.staticCallTest(myLocalCar);
        }



        if(carOpt.isPresent())
            System.out.println("is carOpt null? "+ carOpt.isEmpty() +
                    " : value - " + carOpt.map((car1)-> car1.toString() +
                    "now in lambda").get());
        // map takes in function or lambda that accepts a car as a parameter
        // creates an optional based on the return type (if value is present)
        // if value is null then returns an empty optional

        if(carOpt.isPresent()) {
            System.out.println(carOpt.map(fakeData::staticCallTest).get() + "\nyayyyy!");
            fakeData data = carOpt.map(fakeData::new).get();
            System.out.println(data.getfName() + " : " + data.getAge());
        }


        return "index";
    }

    @GetMapping("/test")
    public String indexTest(Map<String, Object> model, ModelMap map){
        System.out.println("test");
        map.addAttribute("test", "value");
        return "indexTest";
    }

    @GetMapping("/hello") //uri
    public String hello(Map<String, Object> model, ModelMap map){ //mathc
        System.out.println("In Hello");
        map.addAttribute("name", fakeData.name); //putting stuff in
        return "hello"; //return as uri name
    }

    //Form page
    @RequestMapping(value = "/form", method = RequestMethod.GET)
    public String form(Map<String, Object> model, ModelMap map) {
        return "form";
    }
    //, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE
    //@RequestMapping("/form")
    @RequestMapping(value = "/postBlank", method = RequestMethod.POST)
    //submit function
    public String handlePostRequest(@Valid @ModelAttribute("fakeData") fakeData person, BindingResult result) {
        this._fakeData = person;
        System.out.println(result.hasErrors());
        if(result.hasErrors())
            for( ObjectError error : result.getAllErrors()) {
                System.out.println("object error " + error.getDefaultMessage());
                if(error instanceof FieldError){
                    System.out.println(((FieldError)error).getField());
                    System.out.println(((FieldError)error).getObjectName());
                }
            }

//        return String.format("simple response. name: %s, address: %s",
//        personName, personAddress);
        System.out.println("GOING TO REDIRECT");
        return  "redirect:/confirmPost/" + person.getfName() + "/" + new String(person.getAge()).toString();

    }

    //Dealing with the post
    @RequestMapping(value = "/confirmPost/{fName1}/{age1}", method = RequestMethod.GET)
    public String confirmPost(ModelMap map, @PathVariable(value = "", name = "fName1", required = false)
            String personName, @PathVariable(value = "", name = "age1", required = false)
                                      String personAge) {
        map.addAttribute("name", personName);
        map.addAttribute("age", personAge);
        return "confirmPost";
    }

    @RequestMapping(value = "/validationForm", method = RequestMethod.GET)
    public String validationFormGet(){return "validationTest";}

    @RequestMapping(value = "/validationForm/validate", method = RequestMethod.POST)
    public String validationFormValidate(
            @Valid @ModelAttribute("LicensePlate") LicensePlate licensePlate,
            BindingResult result,
            ModelMap modelMap){

        final String PREFIX = "classError: ";

        System.out.println(PREFIX + result.hasErrors());
        if(result.hasErrors())
            for( ObjectError error : result.getAllErrors()) {
                System.out.println(error.getDefaultMessage());
                if(error instanceof FieldError){
                    System.out.println(((FieldError)error).getField());
                    System.out.println(((FieldError)error).getObjectName());
                    System.out.println(((FieldError)error).getDefaultMessage());
                }
                else{
                    System.out.println(PREFIX + error.getObjectName());
                    System.out.println(PREFIX + error.getObjectName());
                    System.out.println(PREFIX + error.getDefaultMessage());
                    System.out.println(PREFIX + error.getCode());

                }
            }

        modelMap.addAttribute("success", result.hasErrors());

        return "validationResult";
    }

    @RequestMapping(path = "/apiTest", method = RequestMethod.GET)
    public String getApiTest(){
        return "apiTest";
    }
}