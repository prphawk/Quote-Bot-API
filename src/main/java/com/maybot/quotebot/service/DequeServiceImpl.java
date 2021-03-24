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
    private final ScheduleServiceImpl scheduleServiceImpl;


    public DequeServiceImpl(QuoteRepository quoteRepository, DequeRepository dequeRepository, ScheduleServiceImpl scheduleServiceImpl) {
        this.quoteRepository = quoteRepository;
        this.dequeRepository = dequeRepository;
        this.scheduleServiceImpl = scheduleServiceImpl;
    }

    public ResponseEntity<List<DequeDataModel>> getDequeRequest() {

        List<Deque> deque = dequeRepository.findAllPriorityFirst();

        List<DequeDataModel> response = deque.stream().map(DequeDataModel::new).collect(Collectors.toList());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public ResponseEntity<QuoteDataModel> popDequeRequest(boolean forcePop) {

        if(scheduleServiceImpl.isItTime() || forcePop) {
            QuoteDataModel response = popDeque();
            if(response != null)
                return new ResponseEntity<>(response, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    public QuoteDataModel popDeque() {

        Optional<Deque> dequeSearch = dequeRepository.findPriorityFirst(PageRequest.of(0, 1));

        if (dequeSearch.isPresent()) {
            Deque deque = dequeSearch.get();

            QuoteDataModel response = new QuoteDataModel(deque.getQuote());

            dequeRepository.deleteById(deque.getId());

            return response;

        } else {
            List<Deque> dequeList = makeNewDeque();

            if (dequeList != null) return popDeque();
        }

        return null;
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

    public ResponseEntity<List<DequeDataModel>> shuffleDequeRequest() {

        List<Deque> dequeList = (List<Deque>) dequeRepository.findAll();

        if(dequeList.size() > 0) {

            List<Quote> quotesFromDeque = dequeList.stream().map(Deque::getQuote).collect(Collectors.toList());

            Collections.shuffle(quotesFromDeque);

            dequeRepository.deleteAll();

            List<Deque> newDequeList = quotesFromDeque.stream().map(Deque::new).collect(Collectors.toList());

            dequeRepository.saveAll(newDequeList);

            return getDequeRequest();

        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<List<DequeDataModel>> editPrioritiesRequest(List<DequeDataModel> dequeList) {
        dequeList.forEach(d -> {
            dequeRepository.findById(d.getId()).ifPresent(deque -> {
                deque.setPriority(d.isPriority());
                dequeRepository.save(deque);
            });
        });

        return getDequeRequest();
    }

}
