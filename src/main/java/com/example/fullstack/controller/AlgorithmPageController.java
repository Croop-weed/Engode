package com.example.fullstack.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AlgorithmPageController {

    @GetMapping(" ")

    public String index() {
        return "index.html"; // Refers to create.html inside templates/
    }
    @GetMapping("/create")
    public String showAlgorithmPage() {
        return "create.html"; // Refers to create.html inside templates/
    }

}

