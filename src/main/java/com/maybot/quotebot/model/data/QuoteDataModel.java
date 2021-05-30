package com.maybot.quotebot.model.data;

import com.maybot.quotebot.constant.DataContants;
import com.maybot.quotebot.entity.Quote;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class QuoteDataModel {

    private Long id;

    @NotBlank(message = DataContants.TWEET_EMPTY_MESSAGE)
    @Size(max = DataContants.TWEET_MAX, message = DataContants.TWEET_MAX_MESSAGE)
    private String text;

    @Size(max = DataContants.TWEET_MAX, message = DataContants.TWEET_MAX_MESSAGE)
    private String source;

    private boolean showSource;

    private boolean invisible;

    private List<String> tags;

    private List<@Length(max = DataContants.TWEET_MAX) String> replies;

    @Valid
    private List<ImageDataModel> images;

    public QuoteDataModel() {
        this.replies = new ArrayList<>();
        this.tags = new ArrayList<>();
        this.images = new ArrayList<>();
    }

    public QuoteDataModel(String text, String source, boolean showSource, boolean invisible, List<String> replies, List<ImageDataModel> images) {
        this.text = text;
        this.source = source;
        this.showSource = showSource;
        this.invisible = invisible;
        this.replies = replies;
        this.images = images;
    }

    public QuoteDataModel(Quote quote) {
        this.id = quote.getId();
        this.text = quote.getText();
        this.source = quote.getSource();
        this.showSource = quote.getShowSource();
        this.replies = quote.getReplies();
        this.invisible = quote.isInvisible();
        this.tags = quote.getTags();
        this.replies = quote.getReplies();
        this.images = quote.getImages().stream().map(ImageDataModel::new).collect(Collectors.toList());
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

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
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

    public List<String> getReplies() {
        return replies;
    }

    public void setReplies(List<String> replies) {
        this.replies = replies;
    }

    public boolean isInvisible() {
        return invisible;
    }

    public void setInvisible(boolean invisible) {
        this.invisible = invisible;
    }

    public List<ImageDataModel> getImages() {
        return images;
    }

    public void setImages(List<ImageDataModel> images) {
        this.images = images;
    }
}
