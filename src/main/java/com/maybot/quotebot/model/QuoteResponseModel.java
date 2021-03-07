package com.maybot.quotebot.model;

import com.maybot.quotebot.entity.Quote;

import java.util.List;
import java.util.stream.Collectors;

public class QuoteResponseModel {

    private Long id;

    private String text;

    private String source;

    private List<ReplyResponseModel> replies;

    public QuoteResponseModel(Quote quote) {
        this.id = quote.getId();
        this.text = quote.getText();
        this.source = quote.getSource();
        if(quote.getReplies() != null)
            this.replies = quote.getReplies().stream().map(ReplyResponseModel::new).collect(Collectors.toList());
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

    public List<ReplyResponseModel> getReplies() {
        return replies;
    }

    public void setReplies(List<ReplyResponseModel> replies) {
        this.replies = replies;
    }
}
