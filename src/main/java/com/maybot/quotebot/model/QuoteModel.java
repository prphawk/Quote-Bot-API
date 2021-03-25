package com.maybot.quotebot.model;

import com.maybot.quotebot.constant.DataContants;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Valid
public class QuoteModel {

    @NotBlank(message = DataContants.QUOTE_TEXT_EMPTY_MESSAGE)
    @Size(max = DataContants.QUOTE_TEXT_MAX, message = DataContants.QUOTE_TEXT_MAX_MESSAGE)
    private String text;

    @Size(max = DataContants.QUOTE_TEXT_MAX, message = DataContants.QUOTE_SOURCE_MAX_MESSAGE)
    private String source;

    private boolean hideSource;

    @Valid
    private List<ReplyModel> replies;

    private boolean priority;

    public QuoteModel() {
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

    public boolean getHideSource() {
        return hideSource;
    }

    public void setHideSource(boolean hideSource) {
        this.hideSource = hideSource;
    }
}
