package com.maybot.quotebot.controller;

import com.maybot.quotebot.model.DequeResponseModel;
import com.maybot.quotebot.model.QuoteResponseModel;
import com.maybot.quotebot.service.DequeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/deque/")
public class DequeControllerImpl {

    private final DequeServiceImpl dequeServiceImpl;

    @Autowired
    public DequeControllerImpl(DequeServiceImpl dequeServiceImpl) { this.dequeServiceImpl = dequeServiceImpl; }

    @GetMapping
    public ResponseEntity<QuoteResponseModel> pop() {
        return dequeServiceImpl.popQuote();
    }

    @GetMapping("all/")
    public ResponseEntity<List<DequeResponseModel>> getAll() {
        return dequeServiceImpl.getDeque();
    }

}
