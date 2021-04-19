package com.maybot.quotebot.model.data;

import com.maybot.quotebot.constant.DataContants;
import com.maybot.quotebot.entity.Reply;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class ReplyDataModel {

    private Long id;

    @NotBlank(message = DataContants.REPLY_TEXT_NULL_MESSAGE)
    @Size(max = DataContants.QUOTE_TEXT_MAX, message = DataContants.QUOTE_TEXT_MAX_MESSAGE)
    private String text;

    public ReplyDataModel() {}

    public ReplyDataModel(@NotBlank(message = DataContants.REPLY_TEXT_NULL_MESSAGE) @Size(max = DataContants.QUOTE_TEXT_MAX, message = DataContants.QUOTE_TEXT_MAX_MESSAGE) String text) {
        this.text = text;
    }

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
