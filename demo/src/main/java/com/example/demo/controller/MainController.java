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

@Controller
// @RequestMapping(path = "/")
public class MainController {

    @GetMapping("/")
    public String mainGet(Map<String, Object> model, ModelMap map){
        System.out.println("test");
        map.addAttribute("test", "value");
        return "index";
    }

}
