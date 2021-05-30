package com.maybot.quotebot.service;

import com.maybot.quotebot.entity.Queue;
import com.maybot.quotebot.entity.Quote;
import com.maybot.quotebot.model.PriorityRequestModel;
import com.maybot.quotebot.model.QuoteRequestModel;
import com.maybot.quotebot.model.data.QueueDataModel;
import com.maybot.quotebot.model.data.QuoteDataModel;
import com.maybot.quotebot.repository.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
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

        List<Queue> queue = queueRepository.getQueue();

        List<QueueDataModel> response = queue.stream().map(QueueDataModel::new).collect(Collectors.toList());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public ResponseEntity<QuoteDataModel> popQueueRequest(boolean forcePop) {

        if(scheduleServiceImpl.isItTime() || forcePop) {
            QuoteDataModel response = popQueue();
            if(response != null) return new ResponseEntity<>(response, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    public QuoteDataModel popQueue() {

        Optional<Queue> queueSearch = queueRepository.pop(PageRequest.of(0, 1));

        if (queueSearch.isPresent()) {
            Queue queue = queueSearch.get();

            QuoteDataModel response = new QuoteDataModel(queue.getQuote());

            queue.setPriority(false);

            queue.setIndex(null);

            queueRepository.save(queue);

            return response;

        } else {
            List<Queue> queueList = makeNewQueue();

            if (queueList != null) return popQueue();
        }

        return null;
    }

    public List<Queue> makeNewQueue() {

        List<Queue> queue = (List<Queue>) queueRepository.findAll();

        if(queue.size() > 0) return shuffleQueue(queue);

        return null;
    }

    public ResponseEntity<Void> deleteQueueRequest() {
        queueRepository.deleteAll();

        return new ResponseEntity<>(HttpStatus.OK);
    }

    public List<Queue> shuffleQueue(List<Queue> queue) {

        List<Long> ids = queue.stream().map(Queue::getId).collect(Collectors.toList());

        Collections.shuffle(ids);

        for (int i = 0; i < queue.size(); i++) {
            queue.get(i).setIndex(ids.get(i));
        }

        return (List<Queue>) queueRepository.saveAll(queue);
    }

    public ResponseEntity<List<QueueDataModel>> shuffleQueueRequest() {

        List<Queue> queueNotPosted = queueRepository.findByIndexNotNull();

        if(queueNotPosted.size() > 0) {

            shuffleQueue(queueNotPosted);

            return getQueueRequest();
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<List<QueueDataModel>> editPrioritiesRequest(List<PriorityRequestModel> models) {

        models.forEach(model -> {

            Optional<Queue> search = queueRepository.findByQuoteId(model.getId());

            if(search.isPresent()) {

                Queue queue = search.get();

                queue.setPriority(model.isPriority());

                queueRepository.save(queue);

            } else quoteRepository.findById(model.getId()).ifPresent(
                    quote -> queueRepository.save(new Queue(quote, model.isPriority())));
        });

        return getQueueRequest();
    }

    public List<QueueDataModel> getAllPosted() {
        List<Queue> queuePosted = queueRepository.findByIndexNull();

        return queuePosted.stream().map(QueueDataModel::new).collect(Collectors.toList());
    }

    public ResponseEntity<List<QueueDataModel>> getAllPostedRequest() {
        List<QueueDataModel> response = getAllPosted();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public void deleteByQuoteId(Long id) {
        queueRepository.findByQuoteId(id).ifPresent(
                queue -> queueRepository.deleteById(queue.getId()));
    }

    public Queue push(Quote quote, boolean isPriority) {
        return queueRepository.save(new Queue(quote, isPriority));
    }

    public ResponseEntity<List<QueueDataModel>> restoreQueue(List<QuoteRequestModel> postedModels) {

        postedModels.forEach(posted ->
            queueRepository.findByQuoteText(posted.getText())
                    .ifPresent(queue -> {
                        queue.setIndex(null);
                        queueRepository.save(queue);
                    })
        );

        return getQueueRequest();
    }
}
