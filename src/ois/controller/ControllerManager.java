package ois.controller;

import java.util.List;

import ois.exceptions.PersistanceManagerException;
import ois.view.ImageLink;

public interface ControllerManager {

	public abstract void createImage(Image img)
			throws PersistanceManagerException;

	public abstract List<Album> getAlbums();

	public abstract void createAlbum(String name, String description)
			throws PersistanceManagerException;

	public abstract void deleteAlbum(String key)
			throws PersistanceManagerException;

	public abstract Album getAlbum(String key)
			throws PersistanceManagerException;
	
	public abstract void saveAlbum(Album album)
			throws PersistanceManagerException;

	/**
	 * Getter for image links that given album has. 
	 * If key is "none" that returns an empty list.
	 * If id is "All" that returns all the imagelinks in system.
	 * @param albumKey Key of album that is owner of the returned images.
	 * @return Image links that given album has
	 * @throws PersistanceManagerException
	 */
	public abstract List<ImageLink> getImageLinks(String albumKey)
	throws PersistanceManagerException;
	
	abstract void close();
}