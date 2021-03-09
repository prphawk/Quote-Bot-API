package com.maybot.quotebot.service;

import com.maybot.quotebot.entity.Deque;
import com.maybot.quotebot.entity.Quote;
import com.maybot.quotebot.entity.Reply;
import com.maybot.quotebot.model.*;
import com.maybot.quotebot.repository.QuoteRepository;
import com.maybot.quotebot.repository.DequeRepository;
import com.maybot.quotebot.repository.ReplyRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class QuoteServiceImpl {

    private final QuoteRepository quoteRepository;
    private final DequeRepository dequeRepository;
    private final ReplyServiceImpl replyServiceImpl;

    public QuoteServiceImpl(QuoteRepository quoteRepository, DequeRepository dequeRepository, ReplyServiceImpl replyServiceImpl) {
        this.quoteRepository = quoteRepository;
        this.dequeRepository = dequeRepository;
        this.replyServiceImpl = replyServiceImpl;
    }

    public ResponseEntity<List<QuoteResponseModel>> getAllRequest() {

        List<Quote> quotes = (List<Quote>) quoteRepository.findAll();

        List<QuoteResponseModel> response = quotes.stream().map(QuoteResponseModel::new).collect(Collectors.toList());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public QuoteResponseModel save(@Valid QuoteModel model) {

        Quote quote = new Quote(model);

        quoteRepository.save(quote);

        QuoteResponseModel response = new QuoteResponseModel(quote);

        response.setReplies(replyServiceImpl.saveReplies(model, quote));

        dequeRepository.save(new Deque(quote, model.isPriority()));

        return response;
    }

    public ResponseEntity<QuoteResponseModel> saveRequest(QuoteModel model) {

        QuoteResponseModel response = save(model);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public ResponseEntity<List<QuoteResponseModel>> saveAllRequest(@Valid AllQuoteModel model) {

        List<QuoteModel> quotes = model.getQuotes();

        if(model.isShuffle()) Collections.shuffle(quotes);

        List<QuoteResponseModel> response = quotes.stream().map(this::save).collect(Collectors.toList());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public ResponseEntity<QuoteResponseModel> editRequest(@Valid QuoteResponseModel model) {

        Optional<Quote> quoteSearch = quoteRepository.findById(model.getId());

        if(quoteSearch.isPresent()) {

            Quote quote = quoteSearch.get();

            QuoteResponseModel response = saveChanges(model, quote);

            response.setReplies(replyServiceImpl.editReplies(model.getReplies(), quote));

            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<Void> deleteRequest(Long id) {

        Optional<Quote> quoteSearch = quoteRepository.findById(id);

        if(quoteSearch.isPresent()) {

            quoteRepository.delete(quoteSearch.get());

            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<Void> deleteAllRequest(List<Long> ids) {

        List<Quote> quotes = (List<Quote>) quoteRepository.findAllById(ids);

        quoteRepository.deleteAll(quotes);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    private QuoteResponseModel saveChanges(QuoteResponseModel model, Quote quote) {

        quote.setText(model.getText());

        quote.setSource(model.getSource());

        return new QuoteResponseModel(quoteRepository.save(quote));
    }
}
