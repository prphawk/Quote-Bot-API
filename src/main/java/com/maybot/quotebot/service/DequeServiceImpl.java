package com.maybot.quotebot.service;

import com.maybot.quotebot.entity.Deque;
import com.maybot.quotebot.entity.Quote;
import com.maybot.quotebot.repository.*;
import com.maybot.quotebot.model.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DequeServiceImpl {

    private final QuoteRepository quoteRepository;
    private final DequeRepository dequeRepository;


    public DequeServiceImpl(QuoteRepository quoteRepository, DequeRepository dequeRepository) {
        this.quoteRepository = quoteRepository;
        this.dequeRepository = dequeRepository;
    }

    public ResponseEntity<List<DequeResponseModel>> getDeque() {

        List<Deque> deque = dequeRepository.findAllPriorityFirst();

        List<DequeResponseModel> response = deque.stream().map(DequeResponseModel::new).collect(Collectors.toList());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public ResponseEntity<QuoteResponseModel> popDequeRequest() {

        Optional<Deque> dequeSearch = dequeRepository.findPriorityFirst(PageRequest.of(0,1));

        if(dequeSearch.isPresent()) {
            Deque deque = dequeSearch.get();

            QuoteResponseModel response = new QuoteResponseModel(deque.getQuote());

            dequeRepository.deleteById(deque.getId());

            return new ResponseEntity<>(response, HttpStatus.OK);

        } else {
            List<Deque> dequeList = makeNewDeque();

            if(dequeList != null) return popDequeRequest();
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    public List<Deque> makeNewDeque() {

        List<Quote> quotes = (List<Quote>) quoteRepository.findAll();

        if(quotes.size() > 0) {
            Collections.shuffle(quotes);

            List<Deque> dequeList = quotes.stream().map(Deque::new).collect(Collectors.toList());

            return (List<Deque>) dequeRepository.saveAll(dequeList);
        }

        return null;
    }

    public ResponseEntity<Void> deleteDequeRequest() {
        dequeRepository.deleteAll();

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
