package ois.images;

import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.api.images.Transform;

public class ImageManipulatorImpl implements ImageManipulator {
	
	public ImageManipulatorImpl(){
		
	}
	
	@Override
	public byte[] resize(byte[] oldImageData, int width, int height) {
        ImagesService imagesService = ImagesServiceFactory.getImagesService();

        com.google.appengine.api.images.Image oldImage = ImagesServiceFactory.makeImage(oldImageData);
        Transform resize = ImagesServiceFactory.makeResize(width, height);

        com.google.appengine.api.images.Image newImage = imagesService.applyTransform(resize, oldImage);
        
        return newImage.getImageData();
	}

	@Override
	public byte[] enhance(byte[] oldImageData) {
        ImagesService imagesService = ImagesServiceFactory.getImagesService();

        com.google.appengine.api.images.Image oldImage = ImagesServiceFactory.makeImage(oldImageData);
        Transform enhance = ImagesServiceFactory.makeImFeelingLucky();

        com.google.appengine.api.images.Image newImage = imagesService.applyTransform(enhance, oldImage);
        
        return newImage.getImageData();
	}

	@Override
	public byte[] resizeandEnhance(byte[] oldImageData, int width, int height) {
        ImagesService imagesService = ImagesServiceFactory.getImagesService();

        com.google.appengine.api.images.Image oldImage = ImagesServiceFactory.makeImage(oldImageData);
        Transform resize = ImagesServiceFactory.makeResize(width, height);
        Transform enhance = ImagesServiceFactory.makeImFeelingLucky();

        com.google.appengine.api.images.Image newImage = imagesService.applyTransform(resize, oldImage);
        newImage = imagesService.applyTransform(enhance, newImage);
        
        return newImage.getImageData();
	}
}
