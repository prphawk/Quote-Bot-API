package com.maybot.quotebot.service;

import com.maybot.quotebot.entity.Image;
import com.maybot.quotebot.entity.Quote;
import com.maybot.quotebot.model.data.ImageDataModel;
import com.maybot.quotebot.model.data.QuoteDataModel;
import com.maybot.quotebot.repository.ImageRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ImageServiceImpl {

    private final ImageRepository imageRepository;

    public ImageServiceImpl(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public ImageDataModel save(ImageDataModel model, Quote quote) {

        Image image = imageRepository.save(new Image(model, quote));

        return new ImageDataModel(image);
    }

    public List<ImageDataModel> saveAll(List<ImageDataModel> models, Quote quote) {

        return models.stream().map(imageDataModel -> save(imageDataModel, quote)).collect(Collectors.toList());
    }

}
