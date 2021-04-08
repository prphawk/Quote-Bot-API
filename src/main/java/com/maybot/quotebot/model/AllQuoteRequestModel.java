package com.maybot.quotebot.model;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

public class AllQuoteRequestModel {

    private boolean shuffle;

    @NotNull
    @Valid
    private List<QuoteRequestModel> quotes;

    public AllQuoteRequestModel() {}

    public AllQuoteRequestModel(List<QuoteRequestModel> quotes) {
        this.shuffle = false;
        this.quotes = quotes;
    }

    public AllQuoteRequestModel(boolean shuffle, List<QuoteRequestModel> quotes) {
        this.shuffle = shuffle;
        this.quotes = quotes;
    }

    public boolean isShuffle() {
        return shuffle;
    }

    public void setShuffle(boolean shuffle) {
        this.shuffle = shuffle;
    }

    public List<QuoteRequestModel> getQuotes() {
        return quotes;
    }

    public void setQuotes(List<QuoteRequestModel> quotes) {
        this.quotes = quotes;
    }
}
