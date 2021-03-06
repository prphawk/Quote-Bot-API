package com.maybot.quotebot.model;

import com.maybot.quotebot.entity.Quote;

public class QuoteResponseModel {

    private Long id;

    private String text;

    private String source;

    private Long replyId;

    public QuoteResponseModel(Quote quote) {
        this.id = quote.getId();
        this.text = quote.getText();
        this.source = quote.getSource();
        Quote reply = quote.getReply();
        if(reply != null)
            this.replyId = reply.getId();
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

    public Long getReplyId() {
        return replyId;
    }

    public void setReplyId(Long replyId) {
        this.replyId = replyId;
    }
}
