package com.maybot.quotebot.model;

import com.maybot.quotebot.constant.DataContants;
import com.maybot.quotebot.entity.Quote;
import com.maybot.quotebot.model.data.ImageDataModel;
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

    public QuoteRequestModel(String text, String source, boolean showSource, boolean invisible, List<ReplyDataModel> replies, List<ImageDataModel> images, boolean priority) {
        super(text, source, showSource, invisible, replies, images);
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
