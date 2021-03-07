package com.maybot.quotebot.controller;

import com.maybot.quotebot.model.QuoteModel;
import com.maybot.quotebot.model.QuoteResponseModel;
import com.maybot.quotebot.service.QuoteServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/quote/")
public class QuoteControllerImpl {

    private final QuoteServiceImpl quoteServiceImpl;

    @Autowired
    public QuoteControllerImpl(QuoteServiceImpl quoteServiceImpl) {
        this.quoteServiceImpl = quoteServiceImpl;
    }

    @GetMapping("all/")
    public ResponseEntity<List<QuoteResponseModel>> getAll() {
        return quoteServiceImpl.getAllRequest();
    }

    @PostMapping
    public ResponseEntity<QuoteResponseModel> save(@Valid @RequestBody QuoteModel model) {
        return quoteServiceImpl.saveRequest(model);
    }

    @PostMapping("all/")
    public ResponseEntity<List<QuoteResponseModel>> saveAll(@Valid @RequestBody List<QuoteModel> models) {
        return quoteServiceImpl.saveAllRequest(models);
    }


}
