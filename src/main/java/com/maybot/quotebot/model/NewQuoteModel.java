package com.maybot.quotebot.model;

import com.maybot.quotebot.entity.Quote;
import com.maybot.quotebot.model.data.QuoteDataModel;

public class NewQuoteModel extends QuoteDataModel {
    private boolean priority;

    public NewQuoteModel() {}

    public NewQuoteModel(Quote quote, boolean priority) {
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
