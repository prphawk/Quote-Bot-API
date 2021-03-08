package com.maybot.quotebot.model;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

public class AllQuoteModel {

    private boolean shuffle;

    @NotNull
    @Valid
    private List<QuoteModel> quotes;

    public AllQuoteModel(boolean shuffle, @NotNull @Valid List<QuoteModel> quotes) {
        this.shuffle = shuffle;
        this.quotes = quotes;
    }

    public boolean isShuffle() {
        return shuffle;
    }

    public void setShuffle(boolean shuffle) {
        this.shuffle = shuffle;
    }

    public List<QuoteModel> getQuotes() {
        return quotes;
    }

    public void setQuotes(List<QuoteModel> quotes) {
        this.quotes = quotes;
    }
}
