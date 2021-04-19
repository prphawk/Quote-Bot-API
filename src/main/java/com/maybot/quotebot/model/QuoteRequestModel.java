package com.maybot.quotebot.model;

import com.maybot.quotebot.constant.DataContants;
import com.maybot.quotebot.entity.Quote;
import com.maybot.quotebot.model.data.QuoteDataModel;
import com.maybot.quotebot.model.data.ReplyDataModel;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;


public class QuoteRequestModel extends QuoteDataModel {
    private boolean priority;

    public QuoteRequestModel() {}

    public QuoteRequestModel(boolean priority) {
        this.priority = priority;
    }

    public QuoteRequestModel(@NotBlank(message = DataContants.QUOTE_TEXT_EMPTY_MESSAGE) @Size(max = DataContants.QUOTE_TEXT_MAX, message = DataContants.QUOTE_TEXT_MAX_MESSAGE) String text, @Size(max = DataContants.QUOTE_TEXT_MAX, message = DataContants.QUOTE_SOURCE_MAX_MESSAGE) String source, boolean showSource, boolean invisible, @Valid List<ReplyDataModel> replies, boolean priority) {
        super(text, source, showSource, invisible, replies);
        this.priority = priority;
    }

    public QuoteRequestModel(Quote quote, boolean priority) {
        super(quote);
        this.priority = priority;
    }

    public boolean isPriority() {
        return priority;
    }

    public void setPriority(boolean priority) {
        this.priority = priority;
    }
}
