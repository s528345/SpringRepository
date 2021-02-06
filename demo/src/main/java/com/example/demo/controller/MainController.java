package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import com.example.demo.fakeData;
import org.springframework.http.MediaType;



@Controller
@RequestMapping(path = "/")
public class MainController {

    private fakeData _fakeData;

    @GetMapping("/")
    public String index(Map<String, Object> model, ModelMap map){
        System.out.println("test");
        map.addAttribute("test", "value");
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


    //submit funciton
    public String handlePostRequest(fakeData person) {
        this._fakeData = person;
        System.out.println(person.getfName());

//        return String.format("simple response. name: %s, address: %s",
//        personName, personAddress);
        System.out.println("GOING TO REDIRECT");
        return  "redirect:/confirmPost/" + person.getfName() + "/" + new String(person.getAge()).toString();

    }

    //Dealing with the post
    @RequestMapping(value = "/confirmPost/{fName}/{age}", method = RequestMethod.GET)
    public String confirmPost(ModelMap map, @PathVariable(value = "", name = "fName", required = false)
            String personName, @PathVariable(value = "", name = "age", required = false)
                                      String personAge) {
        map.addAttribute("name", personName);
        map.addAttribute("age", personAge);
        return "confirmPost";
    }
}