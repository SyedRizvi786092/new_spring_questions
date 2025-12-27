package com.example.a3.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")

public class Controller {
	    

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    
    public String getHome() {
    	return "it works";
    }
    
    }



   