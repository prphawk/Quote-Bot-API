package com.maybot.quotebot.model;

import com.maybot.quotebot.entity.Deque;

public class DequeResponseModel {

    protected Long id;

    private boolean priority;

    private QuoteResponseModel quote;

    public DequeResponseModel() {}

    public DequeResponseModel(Deque deque) {
        this.id = deque.getId();
        this.priority = deque.isPriority();
        if(deque.getQuote() != null) {
            this.quote = new QuoteResponseModel(deque.getQuote());
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public QuoteResponseModel getQuote() {
        return quote;
    }

    public void setQuote(QuoteResponseModel quote) {
        this.quote = quote;
    }

    public boolean isPriority() {
        return priority;
    }

    public void setPriority(boolean priority) {
        this.priority = priority;
    }
}
