package com.maybot.quotebot.controller;

import com.maybot.quotebot.model.PriorityRequestModel;
import com.maybot.quotebot.model.data.QueueDataModel;
import com.maybot.quotebot.model.data.QuoteDataModel;
import com.maybot.quotebot.service.QueueServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.maybot.quotebot.constant.PathConstants.*;

@RestController
public class QueueControllerImpl {

    private final QueueServiceImpl queueServiceImpl;

    @Autowired
    public QueueControllerImpl(QueueServiceImpl queueServiceImpl) {
        this.queueServiceImpl = queueServiceImpl;
    }

    @GetMapping(QUEUE)
    public ResponseEntity<List<QueueDataModel>> get() {
        return queueServiceImpl.getQueueRequest();
    }

    @GetMapping(QUEUE_POSTED)
    public ResponseEntity<List<QueueDataModel>> getPosted() {
        return queueServiceImpl.getAllPostedRequest();
    }

    @PutMapping(QUEUE)
    public ResponseEntity<QuoteDataModel> popQueue() {
        return queueServiceImpl.popQueueRequest(false);
    }

    @PutMapping(QUEUE_FORCE_POP)
    public ResponseEntity<QuoteDataModel> forcePopQueue() {
        return queueServiceImpl.popQueueRequest(true);
    }

    @PutMapping(QUEUE_SHUFFLE)
    public ResponseEntity<List<QueueDataModel>> shuffle() { return queueServiceImpl.shuffleQueueRequest(); }

    @PutMapping(QUEUE_PRIORITY)
    public ResponseEntity<List<QueueDataModel>> editPriorities(@Valid @RequestBody List<PriorityRequestModel> models) {
        return queueServiceImpl.editPrioritiesRequest(models);
    }

    @PutMapping
    public ResponseEntity<List<QueueDataModel>> restoreQueue(@Valid @RequestBody List<QuoteDataModel> postedModels) {
        return queueServiceImpl.restoreQueue(postedModels);
    }

    @DeleteMapping(QUEUE_ALL)
    public ResponseEntity<Void> deleteAll() { return queueServiceImpl.deleteQueueRequest(); }
}
