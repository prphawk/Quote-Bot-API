package com.maybot.quotebot.model;

import com.maybot.quotebot.entity.Deque;

public class DequeResponseModel {

    protected Long id;

    private Long token;

    private QuoteResponseModel quote;

    public DequeResponseModel(Deque deque) {
        this.id = deque.getId();
        this.quote = new QuoteResponseModel(deque.getQuote());
        this.token = deque.getToken();
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

    public Long getToken() {
        return token;
    }

    public void setToken(Long token) {
        this.token = token;
    }
}
