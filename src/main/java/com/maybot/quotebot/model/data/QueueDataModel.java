package com.maybot.quotebot.model;

import com.maybot.quotebot.entity.Deque;

public class DequeDataModel {

    protected Long id;

    private boolean priority;

    private QuoteDataModel quote;

    public DequeDataModel() {}

    public DequeDataModel(Deque deque) {
        this.id = deque.getId();
        this.priority = deque.isPriority();
        if(deque.getQuote() != null) {
            this.quote = new QuoteDataModel(deque.getQuote());
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public QuoteDataModel getQuote() {
        return quote;
    }

    public void setQuote(QuoteDataModel quote) {
        this.quote = quote;
    }

    public boolean isPriority() {
        return priority;
    }

    public void setPriority(boolean priority) {
        this.priority = priority;
    }
}
