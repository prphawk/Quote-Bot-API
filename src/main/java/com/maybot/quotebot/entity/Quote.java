package com.maybot.quotebot.entity;

import com.maybot.quotebot.constant.DataContants;
import com.maybot.quotebot.model.data.QuoteDataModel;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "quote")
public class Quote {

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    protected Long id;

    @Column(name = "text", length = DataContants.TWEET_MAX, nullable = false)
    private String text;

    @Column(name = "source", length = DataContants.TWEET_MAX)
    private String source;

    @Column(name = "showSource")
    private boolean showSource;

    @Column(name = "invisible")
    private boolean invisible;


    @ElementCollection
    @Column(length=DataContants.TWEET_MAX)
    @CollectionTable(name = "replies")
    private List<String> replies;

    @ElementCollection
    @CollectionTable(name = "tags")
    private List<String> tags;

    @OneToOne(mappedBy = "quote", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Queue queue;

    public Quote() {
        this.replies = new ArrayList<>();
        this.tags = new ArrayList<>();
    }

    public Quote(QuoteDataModel model) {
        this.text = model.getText();
        this.source = model.getSource();
        this.showSource = model.getShowSource();
        this.invisible = model.isInvisible();
        this.tags = model.getTags();
        this.replies = model.getReplies();
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
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

    public List<String> getReplies() {
        return replies;
    }

    public void setReplies(List<String> replies) {
        this.replies = replies;
    }

    public boolean getShowSource() {
        return showSource;
    }

    public void setShowSource(boolean showSource) {
        this.showSource = showSource;
    }

    public boolean isInvisible() {
        return invisible;
    }

    public boolean isVisible() {
        return !invisible;
    }

    public void setInvisible(boolean invisible) {
        this.invisible = invisible;
    }
}
