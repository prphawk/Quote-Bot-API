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

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class QuoteServiceImpl {

    private final QuoteRepository quoteRepository;
    private final DequeRepository dequeRepository;
    private final ReplyRepository replyRepository;

    public QuoteServiceImpl(QuoteRepository quoteRepository, DequeRepository dequeRepository, ReplyRepository replyRepository) {
        this.quoteRepository = quoteRepository;
        this.dequeRepository = dequeRepository;
        this.replyRepository = replyRepository;
    }

    public ResponseEntity<List<QuoteResponseModel>> getAllRequest() {

        List<Quote> quotes = (List<Quote>) quoteRepository.findAll();

        List<QuoteResponseModel> response = quotes.stream().map(QuoteResponseModel::new).collect(Collectors.toList());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public QuoteResponseModel save(QuoteModel model) {

        Quote quote = new Quote(model);
        quoteRepository.save(quote);
        QuoteResponseModel response = new QuoteResponseModel(quote);
        response.setReplies(saveReplies(model, quote));
        dequeRepository.save(new Deque(quote, model.isPriority()));

        return response;
    }

    public ResponseEntity<QuoteResponseModel> saveRequest(QuoteModel model) {

        QuoteResponseModel response = save(model);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public ResponseEntity<List<QuoteResponseModel>> saveAllRequest(AllQuoteModel model) {

        List<QuoteModel> quotes = model.getQuotes();

        if(model.isShuffle()) Collections.shuffle(quotes);

        List<QuoteResponseModel> response = quotes.stream().map(this::save).collect(Collectors.toList());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public List<ReplyResponseModel> saveReplies(QuoteModel model, Quote quote) {
        List<ReplyModel> replyModels = model.getReplies();
        if (replyModels != null) {
            return replyModels.stream().map(replyModel -> {
                Reply reply = new Reply(replyModel, quote);
                return new ReplyResponseModel(replyRepository.save(reply));
            }).collect(Collectors.toList());
        }

        return null;
    }

    public ResponseEntity<QuoteResponseModel> editRequest(QuoteResponseModel model) {
        Optional<Quote> quoteSearch = quoteRepository.findById(model.getId());
        if(quoteSearch.isPresent()) {
            Quote quote = quoteSearch.get();
            quote.setText(model.getText());
            quote.setSource(model.getSource());

            /*
            List<Reply> replies = model.getReplies().stream().map(r -> new Reply(r, quote)).collect(Collectors.toList());
            replyRepository.save()
            //quote.setReplies(replies);
            */

            QuoteResponseModel response = new QuoteResponseModel(quoteRepository.save(quote));

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
}
