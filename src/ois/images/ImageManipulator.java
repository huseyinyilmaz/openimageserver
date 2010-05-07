package ois.images;


public interface ImageManipulator {
	
	public abstract byte[] resize(byte[] oldImageData, int width, int height);
	public abstract byte[] resizeandEnhance(byte[] oldImageData, int width, int height);
	public abstract byte[] enhance(byte[] oldImageData);
	
}