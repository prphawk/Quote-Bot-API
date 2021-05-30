package com.maybot.quotebot.entity;

import com.maybot.quotebot.model.data.ImageDataModel;

import javax.persistence.*;

@Entity
@Table(name = "image")
public class Image {

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    protected Long id;

    @Column(name = "fileName", nullable = false)
    private String fileName;

    @Column
    private String altText;

    @ManyToOne
    @JoinColumn(name = "quote_id", nullable = false)
    private Quote quote;

    public Image(String altText, String fileName, Quote quote) {
        this.altText = altText;
        this.fileName = fileName;
        this.quote = quote;
    }

    public Image() {}

    public Image(ImageDataModel model, Quote quote) {
        this.altText = model.getAllText();
        this.fileName = model.getFileName();
        this.quote = quote;
    }

    public String getAltText() {
        return altText;
    }

    public void setAltText(String altText) {
        this.altText = altText;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Quote getQuote() {
        return quote;
    }

    public void setQuote(Quote quote) {
        this.quote = quote;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
