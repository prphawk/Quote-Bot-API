package com.maybot.quotebot.service;

import com.maybot.quotebot.entity.Queue;
import com.maybot.quotebot.entity.Quote;
import com.maybot.quotebot.model.PriorityModel;
import com.maybot.quotebot.model.data.QueueDataModel;
import com.maybot.quotebot.model.data.QuoteDataModel;
import com.maybot.quotebot.repository.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.management.Query;
import javax.swing.text.html.Option;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class QueueServiceImpl {

    private final QuoteRepository quoteRepository;
    private final QueueRepository queueRepository;
    private final ScheduleServiceImpl scheduleServiceImpl;


    public QueueServiceImpl(QuoteRepository quoteRepository, QueueRepository queueRepository, ScheduleServiceImpl scheduleServiceImpl) {
        this.quoteRepository = quoteRepository;
        this.queueRepository = queueRepository;
        this.scheduleServiceImpl = scheduleServiceImpl;
    }

    public ResponseEntity<List<QueueDataModel>> getQueueRequest() {

        List<Queue> queue = queueRepository.findAllPriorityFirst();

        List<QueueDataModel> response = queue.stream().map(QueueDataModel::new).collect(Collectors.toList());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public ResponseEntity<QuoteDataModel> popQueueRequest(boolean forcePop) {

        if(scheduleServiceImpl.isItTime() || forcePop) {
            QuoteDataModel response = popQueue();
            if(response != null)
                return new ResponseEntity<>(response, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    public QuoteDataModel popQueue() {

        Optional<Queue> queueSearch = queueRepository.findPriorityFirst(PageRequest.of(0, 1));

        if (queueSearch.isPresent()) {
            Queue queue = queueSearch.get();

            QuoteDataModel response = new QuoteDataModel(queue.getQuote());

            queueRepository.deleteById(queue.getId());

            return response;

        } else {
            List<Queue> queueList = makeNewQueue();

            if (queueList != null) return popQueue();
        }

        return null;
    }

    public List<Queue> makeNewQueue() {

        List<Quote> quotes = (List<Quote>) quoteRepository.findAllByInvisibleFalse();

        if(quotes.size() > 0) {
            Collections.shuffle(quotes);

            List<Queue> queueList = quotes.stream().map(Queue::new).collect(Collectors.toList());

            return (List<Queue>) queueRepository.saveAll(queueList);
        }

        return null;
    }

    public ResponseEntity<Void> deleteQueueRequest() {
        queueRepository.deleteAll();

        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<List<QueueDataModel>> shuffleQueueRequest() {

        List<Queue> queueNotPriority = queueRepository.findByPriorityFalse();

        if(queueNotPriority.size() > 0) {

            List<Quote> quotesFromQueue = queueNotPriority.stream().map(Queue::getQuote).collect(Collectors.toList());

            Collections.shuffle(quotesFromQueue);

            queueRepository.deleteAll(queueNotPriority);

            List<Queue> newQueueList = quotesFromQueue.stream().map(Queue::new).collect(Collectors.toList());

            queueRepository.saveAll(newQueueList);

            return getQueueRequest();

        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<List<QueueDataModel>> editPrioritiesRequest(List<PriorityModel> models) {
        models.forEach(model -> {
            Optional<Queue> search = queueRepository.findByQuoteId(model.getId());

            if(search.isPresent()) {
                Queue queue = search.get();
                queue.setPriority(model.isPriority());
                queueRepository.save(queue);
            } else {
                quoteRepository.findById(model.getId()).ifPresent(quote -> {
                    queueRepository.save(new Queue(quote, model.isPriority()));
                });
            }
        });

        return getQueueRequest();
    }
}
