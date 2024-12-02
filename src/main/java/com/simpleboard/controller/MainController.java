package com.simpleboard.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class MainController {

    @GetMapping("/admin")
    public String goAdmin() {
        return "admin-page";
    }

}
