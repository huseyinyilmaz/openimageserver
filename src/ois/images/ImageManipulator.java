package ois.images;

import ois.controller.Image;

public interface ImageManipulator {
	
	public abstract byte[] resize(byte[] oldImageData, int width, int height);
	
	
}