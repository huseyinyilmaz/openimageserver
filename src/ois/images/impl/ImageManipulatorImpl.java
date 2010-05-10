package ois.images.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import ois.controller.Data;
import ois.images.ImageManipulator;

import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.api.images.Transform;

public class ImageManipulatorImpl implements ImageManipulator {
	private Map<String,String> contentTypes = new HashMap<String,String>(new String[]{}, new String[]{});
	public ImageManipulatorImpl(){
		
	}
	
	@Override
	public Data resize(Data oldImageData, int width, int height) {
        ImagesService imagesService = ImagesServiceFactory.getImagesService();

        com.google.appengine.api.images.Image oldImage = ImagesServiceFactory.makeImage(oldImageData.getData());
        Transform resize = ImagesServiceFactory.makeResize(width, height);

        com.google.appengine.api.images.Image newImage = imagesService.applyTransform(resize, oldImage);
        Data newImageData = new Data(oldImageData,newImage.getImageData());
        newImageData.setHeight(height);
        newImageData.setWidth(width);
        return newImageData;
	}

	@Override
	public Data enhance(Data oldImageData) {
        ImagesService imagesService = ImagesServiceFactory.getImagesService();

        com.google.appengine.api.images.Image oldImage = ImagesServiceFactory.makeImage(oldImageData.getData());
        Transform enhance = ImagesServiceFactory.makeImFeelingLucky();

        com.google.appengine.api.images.Image newImage = imagesService.applyTransform(enhance, oldImage);
        Data newImageData = new Data(oldImageData,newImage.getImageData());
        newImageData.setEnhanced(true);
        return newImageData;
	}

	@Override
	public Data resizeAndEnhance(Data oldImageData, int width, int height) {
        ImagesService imagesService = ImagesServiceFactory.getImagesService();

        com.google.appengine.api.images.Image oldImage = ImagesServiceFactory.makeImage(oldImageData.getData());
        Transform resize = ImagesServiceFactory.makeResize(width, height);
        Transform enhance = ImagesServiceFactory.makeImFeelingLucky();

        com.google.appengine.api.images.Image newImage = imagesService.applyTransform(resize, oldImage);
        newImage = imagesService.applyTransform(enhance, newImage);
        Data newImageData = new Data(oldImageData,newImage.getImageData());
        newImageData.setHeight(height);
        newImageData.setWidth(width);
        newImageData.setEnhanced(true);
        return newImageData;
	}

	@Override
	public void setImageProperties(Data imageData) {
        com.google.appengine.api.images.Image image = ImagesServiceFactory.makeImage(imageData.getData());
        imageData.setWidth(image.getWidth());
        imageData.setHeight(image.getHeight());
        imageData.setType(image.getFormat().toString());
	}
	
	private String toContentType(String imageFormat){
		return null;
	}
}
