package ois.controller;

import java.util.List;

import ois.exceptions.PersistanceManagerException;

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
	
}