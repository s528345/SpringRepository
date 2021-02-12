package com.example.demo.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
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

@RestController
@RequestMapping(path = "/rest/api/")
public class RestControllerDemo {

    @RequestMapping(path = "/customHeader", method = RequestMethod.GET,
    consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<fakeData> customHeader(@RequestBody Map<String, String> bodyMap) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Custom-Header", "foo");

        System.out.println(bodyMap.get("name") == null || bodyMap.get("age") == null);

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

}
