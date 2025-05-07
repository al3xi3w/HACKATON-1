package org.example.init.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;


/*
@RestController
@RequestMapping("/hello")
public class HelloController {
    @GetMapping("/world")
    public ResponseEntity<String> helloWorld() {
        return new ResponseEntity<>("Hello World", HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<String> helloUser(Principal principal) {
        return new ResponseEntity<>("Hello " + principal.getName(), HttpStatus.OK);
    }
}

 */
