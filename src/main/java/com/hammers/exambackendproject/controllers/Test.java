package com.hammers.exambackendproject.controllers;

import com.hammers.exambackendproject.authentication.Utils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class Test {

    @PreAuthorize("hasAnyAuthority('Admin')")
    @GetMapping
    public ResponseEntity testLogged(){
        return new ResponseEntity("check ok " + Utils.getEmail(), HttpStatus.OK);
    }
}
