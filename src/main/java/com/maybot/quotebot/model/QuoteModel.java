package com.maybot.quotebot.model;

import com.maybot.quotebot.constant.DataContants;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Valid
public class QuoteModel {

    @NotNull(message = DataContants.QUOTE_TEXT_NULL_MESSAGE)
    @Size(min = 5, max = DataContants.QUOTE_TEXT_MAX, message = DataContants.QUOTE_TEXT_MAX_MESSAGE)
    private String text;

    @Size(max = DataContants.QUOTE_SOURCE_MAX, message = DataContants.QUOTE_SOURCE_MAX_MESSAGE)
    private String source;

    @Valid
    private List<ReplyModel> replies;

    private boolean priority;

    public QuoteModel() {
    }

    public QuoteModel(@NotNull(message = DataContants.QUOTE_TEXT_NULL_MESSAGE) @Size(min = 5, max = DataContants.QUOTE_TEXT_MAX, message = DataContants.QUOTE_TEXT_MAX_MESSAGE) String text, @Size(max = DataContants.QUOTE_SOURCE_MAX, message = DataContants.QUOTE_SOURCE_MAX_MESSAGE) String source, @Valid List<ReplyModel> replies, boolean priority) {
        this.text = text;
        this.source = source;
        this.replies = replies;
        this.priority = priority;
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
