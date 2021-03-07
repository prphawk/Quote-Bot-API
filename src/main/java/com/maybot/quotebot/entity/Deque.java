package com.maybot.quotebot.entity;

import javax.persistence.*;

import static javax.persistence.GenerationType.AUTO;

@Entity
@Table(name = "deque")
public class Deque {

    @Id
    @GeneratedValue(strategy = AUTO)
    @Column(name = "id", nullable = false)
    protected Long id;

    @OneToOne
    @JoinColumn(name = "quote_id", nullable = false)
    private Quote quote;

    @Column(name = "token", nullable = false)
    private int token;

    public Deque() {}

    public Deque(Quote quote) {
        this.quote = quote;
    }

    public Deque(Quote quote, int token) {
        this.quote = quote;
        this.token = token;
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

    public int getToken() {
        return token;
    }

    public void setToken(int token) {
        this.token = token;
    }
}
