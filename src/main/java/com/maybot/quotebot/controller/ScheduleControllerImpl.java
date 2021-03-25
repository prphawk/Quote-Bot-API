package com.maybot.quotebot.controller;

import com.maybot.quotebot.model.data.ScheduleDataModel;
import com.maybot.quotebot.service.ScheduleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schedule/")
public class ScheduleControllerImpl {

    private final ScheduleServiceImpl scheduleServiceImpl;

    @Autowired
    public ScheduleControllerImpl(ScheduleServiceImpl scheduleServiceImpl) {
        this.scheduleServiceImpl = scheduleServiceImpl;
    }

    @GetMapping
    public ResponseEntity<List<ScheduleDataModel>> get() {
        return new ResponseEntity<>(scheduleServiceImpl.getSchedule(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<List<ScheduleDataModel>> save(@RequestBody List<Byte> bytes) {
        return new ResponseEntity<>(scheduleServiceImpl.saveSchedule(bytes), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> isItTime() {
        return new ResponseEntity<>(scheduleServiceImpl.isItTime(), HttpStatus.OK);
    }
}
