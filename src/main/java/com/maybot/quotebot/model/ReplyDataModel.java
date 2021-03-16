package com.maybot.quotebot.model;

import com.maybot.quotebot.entity.Reply;

public class ReplyResponseModel {

    private Long id;

    private String text;

    public ReplyResponseModel() {}

    public ReplyResponseModel(Reply reply) {
        this.id = reply.getId();
        this.text = reply.getText();
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
}
