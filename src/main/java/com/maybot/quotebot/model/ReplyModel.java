package com.maybot.quotebot.model;

import com.maybot.quotebot.constant.DataContants;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ReplyModel {

    @NotNull(message = DataContants.QUOTE_TEXT_NULL_MESSAGE)
    @Size(max = DataContants.QUOTE_TEXT_MAX, message = DataContants.QUOTE_TEXT_MAX_MESSAGE)
    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
