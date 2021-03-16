package com.maybot.quotebot.controller;

import com.maybot.quotebot.model.DequeDataModel;
import com.maybot.quotebot.model.QuoteDataModel;
import com.maybot.quotebot.service.DequeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/deque/")
public class DequeControllerImpl {

    private final DequeServiceImpl dequeServiceImpl;

    @Autowired
    public DequeControllerImpl(DequeServiceImpl dequeServiceImpl) {
        this.dequeServiceImpl = dequeServiceImpl;
    }

    @GetMapping
    public ResponseEntity<QuoteDataModel> popDeque() {
        return dequeServiceImpl.popDequeRequest();
    }

    @GetMapping("all")
    public ResponseEntity<List<DequeDataModel>> getAll() {
        return dequeServiceImpl.getDequeRequest();
    }

    @GetMapping("shuffle")
    public ResponseEntity<List<DequeDataModel>> shuffle() { return dequeServiceImpl.shuffleDequeRequest(); }

    @PutMapping("priority")
    public ResponseEntity<List<DequeDataModel>> editPriorities(@Valid @RequestBody List<DequeDataModel> dequeList) {
        return dequeServiceImpl.editPrioritiesRequest(dequeList);
    }

    @DeleteMapping("all")
    public ResponseEntity<Void> deleteAll() { return dequeServiceImpl.deleteDequeRequest(); }
}
