package com.big_fat_package.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorController {

    @RequestMapping(value="/403")
    public String Error403(){
        return "pages/error/403";
    }
}
