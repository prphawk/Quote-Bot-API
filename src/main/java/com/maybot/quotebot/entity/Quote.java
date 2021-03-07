package com.maybot.quotebot.entity;

import com.maybot.quotebot.constant.DataContants;
import com.maybot.quotebot.model.QuoteModel;

import javax.persistence.*;

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

    @OneToMany(mappedBy = "quote", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Reply> replies;

    public Quote() {}

    public Quote(QuoteModel model) {
        this.text = model.getText();
        this.source = model.getSource();
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

    public List<Reply> getReplies() {
        return replies;
    }

    public void setReplies(List<Reply> replies) {
        this.replies = replies;
    }
}
