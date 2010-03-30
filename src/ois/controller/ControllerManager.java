package ois.controller;

import java.util.List;

import ois.exceptions.PersistanceManagerException;
import ois.view.ImageLinksBean;

public interface ControllerManager {

	public abstract void saveImage(Image img)
			throws PersistanceManagerException;

	public abstract Image getImage(String location, String name);

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
	 * @param id Id of album that is owner of the returned images
	 * @return Image links that given album has
	 * @throws PersistanceManagerException
	 */
	public abstract ImageLinksBean getImageLinks(long id)
	throws PersistanceManagerException;
	
}