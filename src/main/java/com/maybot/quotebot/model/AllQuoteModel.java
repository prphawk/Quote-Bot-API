package com.maybot.quotebot.model;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

public class AllQuoteModel {

    private boolean shuffle;

    @NotNull
    @Valid
    private List<NewQuoteModel> quotes;

    public AllQuoteModel() {}

    public AllQuoteModel(List<NewQuoteModel> quotes) {
        this.shuffle = false;
        this.quotes = quotes;
    }

    public AllQuoteModel(boolean shuffle, List<NewQuoteModel> quotes) {
        this.shuffle = shuffle;
        this.quotes = quotes;
    }

    public boolean isShuffle() {
        return shuffle;
    }

    public void setShuffle(boolean shuffle) {
        this.shuffle = shuffle;
    }

    public List<NewQuoteModel> getQuotes() {
        return quotes;
    }

    public void setQuotes(List<NewQuoteModel> quotes) {
        this.quotes = quotes;
    }
}
