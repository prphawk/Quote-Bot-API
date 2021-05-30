package com.maybot.quotebot.model;

import com.maybot.quotebot.model.data.ImageDataModel;
import com.maybot.quotebot.entity.Quote;
import com.maybot.quotebot.constant.DataContants;
import com.maybot.quotebot.model.data.QuoteDataModel;

import javax.validation.Valid;
import java.util.List;


public class QuoteRequestModel extends QuoteDataModel {
    private boolean priority;

    public QuoteRequestModel() {}

    public QuoteRequestModel(String text, String source, boolean showSource, boolean invisible, @Valid List<String> replies, List<ImageDataModel> images, boolean priority) {
        super(text, source, showSource, invisible, replies);
        this.priority = priority;
    }

    public boolean isPriority() {
        return priority;
    }

    public void setPriority(boolean priority) {
        this.priority = priority;
    }
}
