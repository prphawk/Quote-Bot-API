package com.maybot.quotebot.service;

import com.maybot.quotebot.entity.Deque;
import com.maybot.quotebot.repository.*;
import com.maybot.quotebot.model.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class DequeServiceImpl {

    private final QuoteRepository quoteRepository;
    private final DequeRepository dequeRepository;
    private final ReplyRepository replyRepository;


    public DequeServiceImpl(QuoteRepository quoteRepository, DequeRepository dequeRepository, ReplyRepository replyRepository) {
        this.quoteRepository = quoteRepository;
        this.dequeRepository = dequeRepository;
        this.replyRepository = replyRepository;
    }

    public ResponseEntity<List<DequeResponseModel>> getDeque() {
        List<Deque> deque = (List<Deque>) dequeRepository.findAll();
        List<DequeResponseModel> response = deque.stream().map(DequeResponseModel::new).collect(Collectors.toList());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public ResponseEntity<QuoteResponseModel> popQuote() {
        Optional<Deque> dequeSearch = dequeRepository.findFirstByOrderByTokenAsc();

        if(dequeSearch.isPresent()) {
            Deque deque = dequeSearch.get();
            QuoteResponseModel response = new QuoteResponseModel(deque.getQuote());
            dequeRepository.delete(deque);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
