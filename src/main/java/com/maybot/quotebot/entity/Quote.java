package com.maybot.quotebot.entity;

import com.maybot.quotebot.constant.DataContants;
import com.maybot.quotebot.model.data.QuoteDataModel;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "quote")
public class Quote {

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    protected Long id;

    @Column(name = "text", length = DataContants.QUOTE_TEXT_MAX, nullable = false)
    private String text;

    @Column(name = "source", length = DataContants.QUOTE_TEXT_MAX)
    private String source;

    @Column(name = "showSource")
    private boolean showSource;

    @Column(name = "invisible")
    private boolean invisible;

    @OneToMany(mappedBy = "quote", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Reply> replies;

    @OneToOne(mappedBy = "quote", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Queue queue;

    public Quote() {
        this.replies = new ArrayList<>();
    }

    public Quote(QuoteDataModel model) {
        this.text = model.getText();
        this.source = model.getSource();
        this.showSource = model.getShowSource();
        this.invisible = model.isInvisible();
        this.replies = new ArrayList<>();
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

    public List<Reply> getReplies() {
        return replies;
    }

    public void setReplies(List<Reply> replies) {
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
