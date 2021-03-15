package com.maybot.quotebot.entity;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "deque")
public class Deque implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    protected Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quote_id", nullable = false)
    //@OnDelete(action= OnDeleteAction.CASCADE)
    private Quote quote;

    @Column(name = "priority", nullable = false)
    private boolean priority;

    public Deque() {}

    public Deque(Quote quote) {
        this.quote = quote;
        this.priority = false;
    }

    public Deque(Quote quote, boolean priority) {
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
