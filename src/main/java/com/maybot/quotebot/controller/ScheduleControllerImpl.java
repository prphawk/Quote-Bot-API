package com.maybot.quotebot.controller;

import com.maybot.quotebot.model.ScheduleModel;
import com.maybot.quotebot.service.ScheduleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/schedule/")
public class ScheduleControllerImpl {

    private final ScheduleServiceImpl scheduleServiceImpl;

    @Autowired
    public ScheduleControllerImpl(ScheduleServiceImpl scheduleServiceImpl) {
        this.scheduleServiceImpl = scheduleServiceImpl;
    }

    @GetMapping
    public ResponseEntity<ScheduleModel> get() {
        return new ResponseEntity<>(scheduleServiceImpl.getSchedule(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ScheduleModel> save(@Valid @RequestBody ScheduleModel model) {
        return new ResponseEntity<>(scheduleServiceImpl.saveSchedule(model), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> isItTime() {
        return new ResponseEntity<>(scheduleServiceImpl.isItTime(), HttpStatus.OK);
    }
}
