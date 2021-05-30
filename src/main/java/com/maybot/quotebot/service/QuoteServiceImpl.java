package com.maybot.quotebot.service;

import com.maybot.quotebot.entity.Quote;
import com.maybot.quotebot.model.*;
import com.maybot.quotebot.model.data.*;
import com.maybot.quotebot.repository.QuoteRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class QuoteServiceImpl {

    private final QuoteRepository quoteRepository;
    private final QueueServiceImpl queueService;

    public QuoteServiceImpl(QuoteRepository quoteRepository, QueueServiceImpl queueService) {
        this.quoteRepository = quoteRepository;
        this.queueService = queueService;
    }

    public ResponseEntity<List<QuoteDataModel>> getAllRequest() {

        List<Quote> quotes = (List<Quote>) quoteRepository.findAll();

        List<QuoteDataModel> response = quotes.stream().map(QuoteDataModel::new).collect(Collectors.toList());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public ResponseEntity<List<QuoteDataModel>> getAllPostedRequest() {

        List<QueueDataModel> queueDataModels = queueService.getAllPosted();

        List<QuoteDataModel> response = queueDataModels.stream().map(QueueDataModel::getQuote).collect(Collectors.toList());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public QuoteDataModel save(QuoteRequestModel model) {

        Quote quote = new Quote(model);

        quoteRepository.save(quote);

        QuoteDataModel response = new QuoteDataModel(quote);
        
        if(!model.isInvisible())
            queueService.push(quote, model.isPriority());

        return response;
    }

    public QuoteDataModel edit(QuoteDataModel model) {

        Optional<Quote> quoteSearch = quoteRepository.findById(model.getId());

        if(quoteSearch.isPresent()) {

            Quote quote = quoteSearch.get();

            QuoteDataModel response = saveChanges(model, quote);

            return response;
        }

        return null;
    }

    public ResponseEntity<QuoteDataModel> saveRequest(QuoteRequestModel model) {

        QuoteDataModel response = model.getId() == null ? save(model) : edit(model);

        if(response == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public ResponseEntity<List<QuoteDataModel>> saveAllRequest(@Valid AllQuoteRequestModel model) {

        List<QuoteRequestModel> quotes = model.getQuotes();

        if(model.isShuffle()) Collections.shuffle(quotes);

        List<QuoteDataModel> response = quotes.stream()
                .map(q -> q.getId() == null ? save(q) : edit(q))
                .collect(Collectors.toList());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public ResponseEntity<Void> deleteAllRequest() {
        quoteRepository.deleteAll();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<Void> deleteByIdsRequest(List<Long> ids) {

        quoteRepository.deleteAll(quoteRepository.findAllById(ids));

        return new ResponseEntity<>(HttpStatus.OK);
    }

    private QuoteDataModel saveChanges(QuoteDataModel model, Quote quote) {

        quote.setText(model.getText());

        quote.setSource(model.getSource());

        quote.setShowSource(model.getShowSource());

        quote.setReplies(model.getReplies());

        quote.setTags(model.getTags());

        quote.setInvisible(model.isInvisible());

        if(model.isInvisible()) {
            queueService.deleteByQuoteId(model.getId());
        }

        return new QuoteDataModel(quoteRepository.save(quote));
    }

    public ResponseEntity<String> getSourceRequest(SourceRequestModel model) {

        Optional<Quote> quoteSearch = quoteRepository.findFirstByTextStartsWith(model.getText());

        if(quoteSearch.isPresent()) {
            String response = quoteSearch.get().getSource();

            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<?> findByTagRequest(String tag) {

        List<Quote> quoteSearch = quoteRepository.findByTag(tag);

        if(quoteSearch.size() > 0) {
            List<QuoteDataModel> response = quoteSearch.stream().map(QuoteDataModel::new).collect(Collectors.toList());

            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
