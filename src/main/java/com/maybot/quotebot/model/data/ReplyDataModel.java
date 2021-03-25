package com.maybot.quotebot.model.data;

import com.maybot.quotebot.entity.Reply;

public class ReplyDataModel {

    private Long id;

    private String text;

    public ReplyDataModel() {}

    public ReplyDataModel(Reply reply) {
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
