package com.maybot.quotebot.entity;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.GenerationType.AUTO;

@Entity
@Table(name = "quote")
public class Quote {

    @Id
    @GeneratedValue(strategy = AUTO)
    @Column(name = "id", nullable = false)
    protected Long id;

    @Column(name = "text", length = 280, nullable = false)
    private String text;

    @Column(name = "source", length = 280)
    private String source;

    @Column(name = "replies")
    private List<Quote> replies;

    public Quote() {
        //this.replies = new ArrayList<>();
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public List<Quote> getReplies() {
        return replies;
    }

    public void setReplies(List<Quote> replies) {
        this.replies = replies;
    }
}
