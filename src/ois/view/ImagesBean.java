package ois.view;

import java.util.List;

public class ImagesBean {
	/**
	 * This list is uses controller album since it has
	 * same info with view album.
	 */
	private List<Image> images;

	/**
	 * @return the images
	 */
	public List<Image> getImages() {
		return images;
	}

	/**
	 * @param images the images to set
	 */
	public void setImages(List<Image> images) {
		this.images = images;
	}

	public ImagesBean() {
		super();
	}

	/**
	 * @param images
	 */
	public ImagesBean(List<Image> images) {
		this.images = images;
	}
	
}
