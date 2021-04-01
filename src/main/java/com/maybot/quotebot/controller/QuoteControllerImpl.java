package com.maybot.quotebot.controller;

import com.maybot.quotebot.model.AllQuoteModel;
import com.maybot.quotebot.model.NewQuoteModel;
import com.maybot.quotebot.model.data.QuoteDataModel;
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

    @GetMapping("all")
    public ResponseEntity<List<QuoteDataModel>> getAll() {
        return quoteServiceImpl.getAllRequest();
    }

    @PostMapping
    public ResponseEntity<QuoteDataModel> save(@Valid @RequestBody NewQuoteModel model) {
        return quoteServiceImpl.saveRequest(model);
    }

    @PostMapping("all")
    public ResponseEntity<List<QuoteDataModel>> saveAll(@Valid @RequestBody AllQuoteModel model) {
        return quoteServiceImpl.saveAllRequest(model);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteByIds(@Valid @RequestBody List<Long> ids) {
        return quoteServiceImpl.deleteByIdsRequest(ids);
    }

    @DeleteMapping("all")
    public ResponseEntity<Void> deleteAll() {
        return quoteServiceImpl.deleteAllRequest();
    }
}
