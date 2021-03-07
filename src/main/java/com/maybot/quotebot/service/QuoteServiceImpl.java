package com.maybot.quotebot.service;

import com.maybot.quotebot.entity.Deque;
import com.maybot.quotebot.entity.Quote;
import com.maybot.quotebot.entity.Reply;
import com.maybot.quotebot.model.QuoteModel;
import com.maybot.quotebot.model.QuoteResponseModel;
import com.maybot.quotebot.model.ReplyModel;
import com.maybot.quotebot.model.ReplyResponseModel;
import com.maybot.quotebot.repository.QuoteRepository;
import com.maybot.quotebot.repository.DequeRepository;
import com.maybot.quotebot.repository.ReplyRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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

    public QuoteResponseModel save(QuoteModel model, int[] tokens) {

        Quote quote = new Quote(model);
        quoteRepository.save(quote);
        QuoteResponseModel response = new QuoteResponseModel(quote);
        response.setReplies(saveReplies(model, quote));
        pushToDeque(model.isPushFirst(), quote, tokens);

        return response;
    }

    public ResponseEntity<QuoteResponseModel> saveRequest(QuoteModel model) {

        final int[] tokens = {0, 0};
        getFirstAndLastTokens(tokens);

        QuoteResponseModel response = save(model, tokens);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public ResponseEntity<List<QuoteResponseModel>> saveAllRequest(List<QuoteModel> models) {

        final int[] tokens = {0, 0};
        getFirstAndLastTokens(tokens);

        List<QuoteResponseModel> response = models.stream().map(model ->
                save(model, tokens)).collect(Collectors.toList());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public List<ReplyResponseModel> saveReplies(QuoteModel model, Quote quote) {
        List<ReplyModel> replyModels = model.getReplies();
        if (replyModels != null) {
            return replyModels.stream().map(replyModel -> {
                Reply reply = new Reply(replyModel, quote);
                replyRepository.save(reply);
                return new ReplyResponseModel(reply);
            }).collect(Collectors.toList());
        }

        return null;
    }

    public void getFirstAndLastTokens(int[] tokens) {
        Optional<Deque> dequeSearch = dequeRepository.findFirstByOrderByTokenAsc();
        if(dequeSearch.isPresent()) {
            tokens[0] = dequeSearch.get().getToken();
            dequeSearch = dequeRepository.findFirstByOrderByTokenDesc();
            dequeSearch.ifPresent(deque -> tokens[1] = deque.getToken());
        }
    }

    void pushToDeque(boolean pushFirst, Quote quote, int[] tokens) {
        dequeRepository.save(new Deque(quote, pushFirst ? --tokens[0] : ++tokens[1]));
    }
}
