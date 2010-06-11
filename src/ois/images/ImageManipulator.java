package ois.images;

import ois.controller.Data;
import ois.exceptions.ImageDataTooBigException;


public interface ImageManipulator {
	
	public abstract Data resize(Data oldImageData, int width, int height) throws ImageDataTooBigException;
	public abstract Data resizeAndEnhance(Data oldImageData, int width, int height) throws ImageDataTooBigException;
	public abstract Data enhance(Data oldImageData) throws ImageDataTooBigException;
	public abstract void setImageProperties(Data imageData);
	
}