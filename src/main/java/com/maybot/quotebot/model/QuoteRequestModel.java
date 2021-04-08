package com.maybot.quotebot.model;

import com.maybot.quotebot.entity.Quote;
import com.maybot.quotebot.model.data.QuoteDataModel;

public class QuoteRequestModel extends QuoteDataModel {
    private boolean priority;

    public QuoteRequestModel() {}

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
