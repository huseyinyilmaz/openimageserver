package ois.controller;

import java.util.List;

public class AlbumsBean {
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
