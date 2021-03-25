package com.maybot.quotebot.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "queue")
public class Queue implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    protected Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quote_id", nullable = false)
    private Quote quote;

    @Column(name = "priority", nullable = false)
    private boolean priority;

    public Queue() {}

    public Queue(Quote quote) {
        this.quote = quote;
        this.priority = false;
    }

    public Queue(Quote quote, boolean priority) {
        this.quote = quote;
        this.priority = priority;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Quote getQuote() {
        return quote;
    }

    public void setQuote(Quote quote) {
        this.quote = quote;
    }

    public boolean isPriority() {
        return priority;
    }

    public void setPriority(boolean priority) {
        this.priority = priority;
    }
}
