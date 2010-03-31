package ois.view;

import java.util.List;

import ois.controller.Album;

/**
 * @author Huseyin
 * this class is used to pass values to albums.jsp page from controller servlet.
 * It holds album list , current album and image link list. bean is created in controller servlet and
 * read by albums.jsp
 */
public class AlbumsBean {
	
	/**
	 * This list is uses controller album since it has
	 * same info with view album.
	 */
	private List<Album> albums;

	private List<ImageLink> imageLinks;

	private long currentAlbum;

	/**
	 * Creates an empty albums bean
	 */
	public AlbumsBean() {
		super();
	}

	
	
	/**
	 * Creates a new albums bean with given albums in it.
	 * @param albums List of albums that this bean will hold.
	 */
	public AlbumsBean(List<Album> albums) {
		super();
		this.albums = albums;
	}

	

	/**
	 * Creates a new albums bean with given albums ,selected album and list of image links
	 * @param albums  List of albums that this bean will hold.
	 * @param imageLinks List of image links that this bean will hold
	 * @param currentAlbum owner of images that image link is hold. this parameter has to be id of the owner album.
	 */
	public AlbumsBean(List<Album> albums, List<ImageLink> imageLinks,
			long currentAlbum) {
		this.albums = albums;
		this.imageLinks = imageLinks;
		this.currentAlbum = currentAlbum;
	}



	public List<Album> getAlbums() {
		return albums;
	}

	public void setAlbums(List<Album> albums) {
		this.albums = albums;
	}

	/**
	 * @return the images
	 */
	public List<ImageLink> getImages() {
		return imageLinks;
	}

	/**
	 * @param images the images to set
	 */
	public void setImages(List<ImageLink> images) {
		this.imageLinks = images;
	}

	/**
	 * @return the currentAlbum
	 */
	public long getCurrentAlbum() {
		return currentAlbum;
	}

	/**
	 * @param currentAlbum the currentAlbum to set
	 */
	public void setCurrentAlbum(long currentAlbum) {
		this.currentAlbum = currentAlbum;
	}


}
