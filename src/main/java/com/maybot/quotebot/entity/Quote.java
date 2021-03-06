package com.maybot.quotebot.entity;

import com.maybot.quotebot.constant.DataContants;
import com.maybot.quotebot.model.QuoteModel;

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

    @Column(name = "text", length = DataContants.QUOTE_TEXT_MAX, nullable = false)
    private String text;

    @Column(name = "source", length = DataContants.QUOTE_SOURCE_MAX)
    private String source;

    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "reply_id")
    private Quote reply;

    public Quote() {}

    public Quote(QuoteModel model) {
        this.text = model.getText();
        this.source = model.getSource();
        QuoteModel reply = model.getReply();
        if(reply != null)
            this.reply = new Quote(reply);
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

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Quote getReply() {
        return reply;
    }

    public void setReply(Quote reply) {
        this.reply = reply;
    }
}
