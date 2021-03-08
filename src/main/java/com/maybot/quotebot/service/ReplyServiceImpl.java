package com.maybot.quotebot.service;

import com.maybot.quotebot.entity.Quote;
import com.maybot.quotebot.entity.Reply;
import com.maybot.quotebot.model.QuoteModel;
import com.maybot.quotebot.model.ReplyModel;
import com.maybot.quotebot.model.ReplyResponseModel;
import com.maybot.quotebot.repository.QuoteRepository;
import com.maybot.quotebot.repository.ReplyRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReplyServiceImpl {

    private final QuoteRepository quoteRepository;
    private final ReplyRepository replyRepository;


    public ReplyServiceImpl(QuoteRepository quoteRepository, ReplyRepository replyRepository) {
        this.quoteRepository = quoteRepository;
        this.replyRepository = replyRepository;
    }

    public List<ReplyResponseModel> saveReplies(QuoteModel model, Quote quote) {
        List<ReplyModel> replyModels = model.getReplies();
        if(replyModels != null) {
            return replyModels.stream().map(replyModel -> saveReply(replyModel, quote)).collect(Collectors.toList());
        }
        return null;
    }

    private ReplyResponseModel saveReply(ReplyModel model, Quote quote) {
        Reply reply = new Reply(model, quote);
        return new ReplyResponseModel(replyRepository.save(reply));
    }

    private ReplyResponseModel saveReply(ReplyResponseModel model, Quote quote) {
        Reply reply = new Reply(model, quote);
        return new ReplyResponseModel(replyRepository.save(reply));
    }

    public List<ReplyResponseModel> editReplies(List<ReplyResponseModel> models, Quote quote) {
        return models.stream().map(model -> {
            if(model.getId() != null) {
                Optional<Reply> replySearch = replyRepository.findById(model.getId());
                if (replySearch.isPresent()) {
                    return new ReplyResponseModel(saveChanges(replySearch.get(), model, quote));
                }
            }
            return saveReply(model, quote);
        }).collect(Collectors.toList());
    }

    private Reply saveChanges(Reply reply, ReplyResponseModel model, Quote quote) {
        reply.setText(model.getText());
        reply.setQuote(quote);
        return replyRepository.save(reply);
    }
}
