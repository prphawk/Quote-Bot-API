package com.maybot.quotebot.model;

import com.maybot.quotebot.constant.DataContants;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

public class QuoteModel {

    @NotNull(message = DataContants.QUOTE_TEXT_NULL_MESSAGE)
    @Size(max = DataContants.QUOTE_TEXT_MAX, message = DataContants.QUOTE_TEXT_MAX_MESSAGE)
    private String text;

    @NotNull(message = DataContants.QUOTE_TEXT_NULL_MESSAGE)
    @Size(max = DataContants.QUOTE_TEXT_MAX, message = DataContants.QUOTE_TEXT_MAX_MESSAGE)
    private String source;

    private List<ReplyModel> replies;

    private boolean priority;

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

    public List<ReplyModel> getReplies() {
        return replies;
    }

    public void setReplies(List<ReplyModel> replies) {
        this.replies = replies;
    }

    public boolean isPriority() {
        return priority;
    }

    public void setPriority(boolean priority) {
        this.priority = priority;
    }
}
