package ois.images.impl;

import java.util.HashMap;
import java.util.Map;

import ois.controller.Data;
import ois.exceptions.ImageDataTooBigException;
import ois.images.ImageManipulator;

import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.api.images.Transform;

public class ImageManipulatorImpl implements ImageManipulator {
	private Map<String,String> contentTypes;
	public ImageManipulatorImpl(){
		contentTypes = new HashMap<String,String>();
		contentTypes.put("GIF", "image/gif");
		contentTypes.put("PNG", "image/png");
		contentTypes.put("JPEG", "image/jpeg");
	}
	
	@Override
	public Data resize(Data oldImageData, int width, int height) throws ImageDataTooBigException {
        ImagesService imagesService = ImagesServiceFactory.getImagesService();

        com.google.appengine.api.images.Image oldImage = ImagesServiceFactory.makeImage(oldImageData.getData());
        Transform resize = ImagesServiceFactory.makeResize(width, height);

        com.google.appengine.api.images.Image newImage;
        try{
        	newImage= imagesService.applyTransform(resize, oldImage);
        }catch(Exception e){
        	throw new ImageDataTooBigException("Transformed image is too big(Limit 1MB)");
        }
        Data newImageData = new Data(oldImageData,newImage.getImageData());
        newImageData.setHeight(newImage.getHeight());
        newImageData.setWidth(newImage.getWidth());
        return newImageData;
	}

	@Override
	public Data enhance(Data oldImageData) throws ImageDataTooBigException {
        ImagesService imagesService = ImagesServiceFactory.getImagesService();

        com.google.appengine.api.images.Image oldImage = ImagesServiceFactory.makeImage(oldImageData.getData());
        Transform enhance = ImagesServiceFactory.makeImFeelingLucky();

        com.google.appengine.api.images.Image newImage;
        try{        
        	newImage = imagesService.applyTransform(enhance, oldImage);
        }catch(Exception e){
        	throw new ImageDataTooBigException("Transformed image is too big(Limit 1MB)");
        }

        Data newImageData = new Data(oldImageData,newImage.getImageData());

        setImageProperties(newImageData);
        newImageData.setEnhanced(true);
        return newImageData;
	}

	@Override
	public Data resizeAndEnhance(Data oldImageData, int width, int height) throws ImageDataTooBigException {
		ImagesService imagesService = ImagesServiceFactory.getImagesService();
        com.google.appengine.api.images.Image oldImage = ImagesServiceFactory.makeImage(oldImageData.getData());
        if(oldImage.getHeight()==height && oldImage.getWidth()==width)
        	return enhance(oldImageData);
        Transform resize = ImagesServiceFactory.makeResize(width, height);
        Transform enhance = ImagesServiceFactory.makeImFeelingLucky();

        com.google.appengine.api.images.Image newImage; 
        try{
        	newImage = imagesService.applyTransform(resize, oldImage);
        	newImage = imagesService.applyTransform(enhance, newImage);
        }catch(Exception e){
        	throw new ImageDataTooBigException("Transformed image is too big(Limit 1MB)");
        }
        Data newImageData = new Data(oldImageData,newImage.getImageData());
        setImageProperties(newImageData);
        newImageData.setEnhanced(true);
        return newImageData;
	}

	@Override
	public void setImageProperties(Data imageData) {
        com.google.appengine.api.images.Image image = ImagesServiceFactory.makeImage(imageData.getData());
        imageData.setWidth(image.getWidth());
        imageData.setHeight(image.getHeight());
        imageData.setType(toContentType(image.getFormat().toString()));
	}
	
	private String toContentType(String imageFormat){
		return contentTypes.get(imageFormat);
	}
}
