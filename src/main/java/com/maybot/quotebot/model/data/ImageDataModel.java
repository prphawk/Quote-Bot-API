package com.maybot.quotebot.model.data;

import com.maybot.quotebot.constant.DataContants;
import com.maybot.quotebot.entity.Image;

import javax.validation.constraints.NotBlank;

public class ImageDataModel {

    private Long id;

    private String allText;

    @NotBlank(message = DataContants.IMAGE_FILE_NAME_NULL_MESSAGE)
    private String fileName;

    public ImageDataModel() { }

    public ImageDataModel(Image image) {
        this.id = image.getId();
        this.allText = image.getAltText();
        this.fileName = image.getFileName();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAllText() {
        return allText;
    }

    public void setAllText(String allText) {
        this.allText = allText;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
