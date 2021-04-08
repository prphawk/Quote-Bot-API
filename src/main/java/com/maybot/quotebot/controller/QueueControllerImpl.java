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

@RestController
@RequestMapping("/queue/")
public class QueueControllerImpl {

    private final QueueServiceImpl queueServiceImpl;

    @Autowired
    public QueueControllerImpl(QueueServiceImpl queueServiceImpl) {
        this.queueServiceImpl = queueServiceImpl;
    }

    @GetMapping
    public ResponseEntity<List<QueueDataModel>> get() {
        return queueServiceImpl.getQueueRequest();
    }

    @PutMapping
    public ResponseEntity<QuoteDataModel> popQueue() {
        return queueServiceImpl.popQueueRequest(false);
    }

    @PutMapping("force-pop")
    public ResponseEntity<QuoteDataModel> forcePopQueue() {
        return queueServiceImpl.popQueueRequest(true);
    }

    @PutMapping("shuffle")
    public ResponseEntity<List<QueueDataModel>> shuffle() { return queueServiceImpl.shuffleQueueRequest(); }

    @PutMapping("priority")
    public ResponseEntity<List<QueueDataModel>> editPriorities(@Valid @RequestBody List<PriorityRequestModel> models) {
        return queueServiceImpl.editPrioritiesRequest(models);
    }

    @DeleteMapping("all")
    public ResponseEntity<Void> deleteAll() { return queueServiceImpl.deleteQueueRequest(); }
}
