package com.maybot.quotebot.model;

import com.maybot.quotebot.constant.DataContants;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class QuoteModel {

    @NotNull(message = DataContants.QUOTE_TEXT_NULL_MESSAGE)
    @Size(max = DataContants.QUOTE_TEXT_MAX, message = DataContants.QUOTE_TEXT_MAX_MESSAGE)
    private String text;

    @NotNull(message = DataContants.QUOTE_TEXT_NULL_MESSAGE)
    @Size(max = DataContants.QUOTE_TEXT_MAX, message = DataContants.QUOTE_TEXT_MAX_MESSAGE)
    private String source;

    private QuoteModel reply;

    private boolean pushFirst;

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

    public QuoteModel getReply() {
        return reply;
    }

    public void setReply(QuoteModel reply) {
        this.reply = reply;
    }
}
