package ois.view;

import java.util.List;

public class ImageLinksBean {
	/**
	 * This list is uses controller album since it has
	 * same info with view album.
	 */
	private List<ImageLink> images;

	/**
	 * @return the images
	 */
	public List<ImageLink> getImages() {
		return images;
	}

	/**
	 * @param images the images to set
	 */
	public void setImages(List<ImageLink> images) {
		this.images = images;
	}

	public ImageLinksBean() {
		super();
	}

	/**
	 * @param images
	 */
	public ImageLinksBean(List<ImageLink> images) {
		this.images = images;
	}
	
}
