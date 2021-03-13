package com.maybot.quotebot.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/")
public class ConnectionImpl {

    @GetMapping
    public ResponseEntity<String> get() {
        Date date = new Date();
        String response = "Tudo top! " + date.toString();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
