package com.maybot.quotebot.service;

import com.maybot.quotebot.entity.Quote;
import com.maybot.quotebot.model.QuoteModel;
import com.maybot.quotebot.model.QuoteResponseModel;
import com.maybot.quotebot.repository.QuoteRepository;
import com.maybot.quotebot.repository.DequeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuoteServiceImpl {

    private final QuoteRepository quoteRepository;
    private final DequeRepository dequeRepository;


    public QuoteServiceImpl(QuoteRepository quoteRepository, DequeRepository dequeRepository) {
        this.quoteRepository = quoteRepository;
        this.dequeRepository = dequeRepository;
    }

    public ResponseEntity<List<QuoteResponseModel>> getAllRequest() {

        List<Quote> quotes = (List<Quote>) quoteRepository.findAll();

        List<QuoteResponseModel> response = quotes.stream().map(QuoteResponseModel::new).collect(Collectors.toList());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public ResponseEntity<QuoteResponseModel> saveRequest(QuoteModel model) {

        Quote quote = new Quote(model);

        QuoteResponseModel response = new QuoteResponseModel(quoteRepository.save(quote));

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public ResponseEntity<List<QuoteResponseModel>> saveAllRequest(List<QuoteModel> models) {

        List<QuoteResponseModel> response = saveAll(models);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public List<QuoteResponseModel> saveAll(List<QuoteModel> models) {

        List<Quote> quotes = models.stream().map(Quote::new).collect(Collectors.toList());

        quotes = (List<Quote>) quoteRepository.saveAll(quotes);

        List<QuoteResponseModel> response = quotes.stream().map(QuoteResponseModel::new).collect(Collectors.toList());

        return response;
    }

    /*
    public ResponseEntity<QuoteResponseModel> saveReply(Quote fatherQuote, QuoteModel replyModel) {

        Quote replyQuote = new Quote(replyModel);
        replyQuote = quoteRepository.save(quote);
        QuoteResponseModel response = new QuoteResponseModel(quote);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    */

}
