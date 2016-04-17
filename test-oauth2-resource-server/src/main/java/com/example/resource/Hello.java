package com.example.resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by bertrand on 17/04/2016.
 */
@RestController
public class Hello {

    @RequestMapping("/hello")
    public String hello(){
        return "Hello World !";
    }
}
