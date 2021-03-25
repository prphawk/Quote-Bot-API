package com.maybot.quotebot.service;

import com.maybot.quotebot.entity.Queue;
import com.maybot.quotebot.entity.Quote;
import com.maybot.quotebot.model.*;
import com.maybot.quotebot.model.data.QuoteDataModel;
import com.maybot.quotebot.repository.QuoteRepository;
import com.maybot.quotebot.repository.QueueRepository;
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
    private final QueueRepository queueRepository;
    private final ReplyServiceImpl replyServiceImpl;

    public QuoteServiceImpl(QuoteRepository quoteRepository, QueueRepository queueRepository, ReplyServiceImpl replyServiceImpl) {
        this.quoteRepository = quoteRepository;
        this.queueRepository = queueRepository;
        this.replyServiceImpl = replyServiceImpl;
    }

    public ResponseEntity<List<QuoteDataModel>> getAllRequest() {

        List<Quote> quotes = (List<Quote>) quoteRepository.findAll();

        List<QuoteDataModel> response = quotes.stream().map(QuoteDataModel::new).collect(Collectors.toList());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public QuoteDataModel save(@Valid QuoteModel model) {

        Quote quote = new Quote(model);

        quoteRepository.save(quote);

        QuoteDataModel response = new QuoteDataModel(quote);

        response.setReplies(replyServiceImpl.saveReplies(model, quote));

        queueRepository.save(new Queue(quote, model.isPriority()));

        return response;
    }

    public ResponseEntity<QuoteDataModel> saveRequest(QuoteModel model) {

        QuoteDataModel response = save(model);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public ResponseEntity<List<QuoteDataModel>> saveAllRequest(@Valid AllQuoteModel model) {

        List<QuoteModel> quotes = model.getQuotes();

        if(model.isShuffle()) Collections.shuffle(quotes);

        List<QuoteDataModel> response = quotes.stream().map(this::save).collect(Collectors.toList());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public ResponseEntity<QuoteDataModel> editRequest(@Valid QuoteDataModel model) {

        Optional<Quote> quoteSearch = quoteRepository.findById(model.getId());

        if(quoteSearch.isPresent()) {

            Quote quote = quoteSearch.get();

            QuoteDataModel response = saveChanges(model, quote);

            response.setReplies(replyServiceImpl.editReplies(model.getReplies(), quote));

            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
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

        return new QuoteDataModel(quoteRepository.save(quote));
    }
}
