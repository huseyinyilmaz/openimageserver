package ois.images;

import ois.controller.Data;


public interface ImageManipulator {
	
	public abstract Data resize(Data oldImageData, int width, int height);
	public abstract Data resizeAndEnhance(Data oldImageData, int width, int height);
	public abstract Data enhance(Data oldImageData);
	public abstract void setImageProperties(Data imageData);
	
}