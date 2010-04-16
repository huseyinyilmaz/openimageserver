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

	public abstract void deleteAlbum(long id)
			throws PersistanceManagerException;

	public abstract Album getAlbum(long id)
			throws PersistanceManagerException;
	
	public abstract void saveAlbum(Album album)
			throws PersistanceManagerException;

	/**
	 * Getter for image links that given album has. 
	 * If id is 0 that returns an empty list.
	 * If id is -1 that returns all the imagelinks in system.
	 * @param id Id of album that is owner of the returned images
	 * @return Image links that given album has
	 * @throws PersistanceManagerException
	 */
	public abstract List<ImageLink> getImageLinks(long id)
	throws PersistanceManagerException;
	
	abstract void close();
}