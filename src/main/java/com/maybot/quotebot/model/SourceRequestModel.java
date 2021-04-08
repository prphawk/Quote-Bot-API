package com.maybot.quotebot.model;

import javax.validation.constraints.NotBlank;

public class SourceRequestModel {

    @NotBlank
    private String text;

    public SourceRequestModel() {}

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
