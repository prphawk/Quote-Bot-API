package com.maybot.quotebot.model.data;

import com.maybot.quotebot.entity.Queue;

public class QueueDataModel {

    protected Long id;

    private boolean priority;

    private QuoteDataModel quote;

    public QueueDataModel() {}

    public QueueDataModel(Queue queue) {
        this.id = queue.getId();
        this.priority = queue.getPriority();
        if(queue.getQuote() != null) {
            this.quote = new QuoteDataModel(queue.getQuote());
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
