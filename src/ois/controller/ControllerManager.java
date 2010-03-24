package ois.controller;

import java.util.List;

import ois.exceptions.PersistanceManagerException;

public interface ControllerManager {

	public static final String IMAGE_URI_PREFIX = "/data/";

	public abstract void saveImage(Image img)
			throws PersistanceManagerException;

	public abstract Image getImage(String location, String name);

	/**
	 * returns all album names in the system
	 * @return name of all albums in the system.
	 */
	public abstract List<Album> getAlbums();

	public abstract void createAlbum(String name, String description)
			throws PersistanceManagerException;

}