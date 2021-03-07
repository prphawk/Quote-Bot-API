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

    public ResponseEntity<List<QuoteResponseModel>> saveAllRequest(List<QuoteModel> models) {

        final long[] tokens = {0L, 0L};
        Optional<Deque> dequeSearch = dequeRepository.findFirstByOrderByTokenAsc();
        dequeSearch.ifPresent(deque -> tokens[0] = deque.getToken() - 1);
        dequeSearch = dequeRepository.findFirstByOrderByTokenDesc();
        dequeSearch.ifPresent(deque -> tokens[1] = deque.getToken() + 1);

        List<QuoteResponseModel> response = models.stream().map(model -> {
            Quote quote = new Quote(model);
            quoteRepository.save(quote);
            QuoteResponseModel quoteResponseModel = new QuoteResponseModel(quote);
            quoteResponseModel.setReplies(saveReplies(model, quote));
            saveToken(model, quote, tokens);
            return quoteResponseModel;
        }).collect(Collectors.toList());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    void saveToken(QuoteModel model, Quote quote, long[] tokens) {
        if(model.isPushFirst()) {
            tokens[0]-= 1;
            dequeRepository.save(new Deque(quote, tokens[0]));
        } else {
            tokens[1]+= 1;
            dequeRepository.save(new Deque(quote, tokens[1]));
        }
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
}
