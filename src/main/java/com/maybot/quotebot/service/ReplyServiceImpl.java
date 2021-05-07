package com.maybot.quotebot.service;

import com.maybot.quotebot.entity.Quote;
import com.maybot.quotebot.entity.Reply;
import com.maybot.quotebot.model.data.QuoteDataModel;
import com.maybot.quotebot.model.data.ReplyDataModel;
import com.maybot.quotebot.repository.ReplyRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReplyServiceImpl {

    private final ReplyRepository replyRepository;

    public ReplyServiceImpl(ReplyRepository replyRepository) {
        this.replyRepository = replyRepository;
    }

    public ReplyDataModel saveReply(ReplyDataModel model, Quote quote) {

        Reply reply = new Reply(model, quote);

        return new ReplyDataModel(replyRepository.save(reply));
    }

    public List<ReplyDataModel> saveReplies(List<ReplyDataModel> models, Quote quote) {

        return  models.stream().map(replyDataModel -> saveReply(replyDataModel, quote)).collect(Collectors.toList());
    }

    public List<ReplyDataModel> editReplies(List<ReplyDataModel> models, Quote quote) {

        return models.stream().map(model -> {

            if(model.getId() != null) {

                Optional<Reply> replySearch = replyRepository.findById(model.getId());

                if (replySearch.isPresent())
                    return saveChanges(replySearch.get(), model, quote);
            }
            return saveReply(model, quote);

        }).collect(Collectors.toList());
    }

    private ReplyDataModel saveChanges(Reply reply, ReplyDataModel model, Quote quote) {

        reply.setText(model.getText());

        reply.setQuote(quote);

        return new ReplyDataModel(replyRepository.save(reply));
    }

}
