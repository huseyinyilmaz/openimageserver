package ois.view;

import java.util.List;

import ois.controller.Album;

public class AlbumsBean {
	
	/**
	 * This list is uses controller album since it has
	 * same info with view album.
	 */
	private List<Album> albums;

	public List<Album> getAlbums() {
		return albums;
	}

	public void setAlbums(List<Album> albums) {
		this.albums = albums;
	}

	public AlbumsBean(List<Album> albums) {
		super();
		this.albums = albums;
	}

	public AlbumsBean() {
		super();
	}
}
