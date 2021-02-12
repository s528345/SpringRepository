package com.example.demo.controller;

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


@Controller
@RequestMapping(path = "/")
public class MainController {

    private fakeData _fakeData;

    @GetMapping("/")
    public String index(Map<String, Object> model, ModelMap map){
        System.out.println("test");
        map.addAttribute("test", "value");
        System.out.println("12a".toUpperCase(Locale.ROOT));
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
                System.out.println(error.getDefaultMessage());
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
}