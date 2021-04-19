package com.maybot.quotebot.model.data;

import com.maybot.quotebot.constant.DataContants;
import com.maybot.quotebot.entity.Quote;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class QuoteDataModel {

    private Long id;

    @NotBlank(message = DataContants.QUOTE_TEXT_EMPTY_MESSAGE)
    @Size(max = DataContants.QUOTE_TEXT_MAX, message = DataContants.QUOTE_TEXT_MAX_MESSAGE)
    private String text;

    @Size(max = DataContants.QUOTE_TEXT_MAX, message = DataContants.QUOTE_SOURCE_MAX_MESSAGE)
    private String source;

    private boolean showSource;

    private boolean invisible;

    @Valid
    private List<ReplyDataModel> replies;

    public QuoteDataModel() {
        this.replies = new ArrayList<>();
    }

    public QuoteDataModel(@NotBlank(message = DataContants.QUOTE_TEXT_EMPTY_MESSAGE) @Size(max = DataContants.QUOTE_TEXT_MAX, message = DataContants.QUOTE_TEXT_MAX_MESSAGE) String text, @Size(max = DataContants.QUOTE_TEXT_MAX, message = DataContants.QUOTE_SOURCE_MAX_MESSAGE) String source, boolean showSource, boolean invisible, @Valid List<ReplyDataModel> replies) {
        this.text = text;
        this.source = source;
        this.showSource = showSource;
        this.invisible = invisible;
        this.replies = replies;
    }

    public QuoteDataModel(Quote quote) {
        this.id = quote.getId();
        this.text = quote.getText();
        this.source = quote.getSource();
        this.showSource = quote.getShowSource();
        this.replies = quote.getReplies().stream().map(ReplyDataModel::new).collect(Collectors.toList());
        this.invisible = quote.isInvisible();
    }

    public boolean getShowSource() {
        return showSource;
    }

    public void setShowSource(boolean showSource) {
        this.showSource = showSource;
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

    public List<ReplyDataModel> getReplies() {
        return replies;
    }

    public void setReplies(List<ReplyDataModel> replies) {
        this.replies = replies;
    }

    public boolean isInvisible() {
        return invisible;
    }

    public void setInvisible(boolean invisible) {
        this.invisible = invisible;
    }

}
