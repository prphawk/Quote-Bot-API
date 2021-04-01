package com.maybot.quotebot.entity;

import com.maybot.quotebot.constant.DataContants;
import com.maybot.quotebot.model.data.ReplyDataModel;

import javax.persistence.*;

@Entity
public class Reply {

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    protected Long id;

    @Column(name = "text", length = DataContants.QUOTE_TEXT_MAX, nullable = false)
    private String text;

    @ManyToOne
    @JoinColumn(name = "quote_id", nullable = false)
    private Quote quote;

    public Reply() {}

    public Reply(ReplyDataModel model, Quote quote) {
        this.text = model.getText();
        this.quote = quote;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Quote getQuote() {
        return quote;
    }

    public void setQuote(Quote quote) {
        this.quote = quote;
    }
}
