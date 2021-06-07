package com.maybot.quotebot.model;

import javax.validation.constraints.NotBlank;

public class SearchRequestModel {

    @NotBlank
    private String text;

    public SearchRequestModel() {}

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
