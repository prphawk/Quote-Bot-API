package com.maybot.quotebot.controller;

import com.maybot.quotebot.model.AllQuoteRequestModel;
import com.maybot.quotebot.model.QuoteRequestModel;
import com.maybot.quotebot.model.SearchRequestModel;
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

    @GetMapping("all/posted")
    public ResponseEntity<List<QuoteDataModel>> getAllPosted() {
        return quoteServiceImpl.getAllPostedRequest();
    }

    @GetMapping("source")
    public ResponseEntity<String> getSourceRequest(@Valid @RequestBody SearchRequestModel model) {
        return quoteServiceImpl.getSourceRequest(model);
    }

    @GetMapping("tag")
    public ResponseEntity<?> findByTagRequest(@Valid @RequestBody SearchRequestModel model) {
        return quoteServiceImpl.findByTagRequest(model);
    }

    @GetMapping("find")
    public ResponseEntity<?> findRequest(@Valid @RequestBody SearchRequestModel model) {
        return quoteServiceImpl.findRequest(model);
    }

    @PostMapping
    public ResponseEntity<QuoteDataModel> save(@Valid @RequestBody QuoteRequestModel model) {
        return quoteServiceImpl.saveRequest(model);
    }

    @PostMapping("all")
    public ResponseEntity<List<QuoteDataModel>> saveAll(@Valid @RequestBody AllQuoteRequestModel model) {
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
