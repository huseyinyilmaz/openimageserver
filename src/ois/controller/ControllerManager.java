package ois.controller;

import java.util.List;

import ois.exceptions.PersistanceManagerException;
import ois.view.AlbumBean;
import ois.view.ImageBean;

public interface ControllerManager {

	public abstract void createImage(Image img)
			throws PersistanceManagerException;

	public abstract List<AlbumBean> getAlbumBeanList();

	public abstract void createAlbum(String name, String description)
			throws PersistanceManagerException;

	public abstract void deleteAlbum(String key)
			throws PersistanceManagerException;

	public abstract Album getAlbum(String key)
			throws PersistanceManagerException;
	
	public abstract AlbumBean getAlbumBean(String key) throws PersistanceManagerException;
	
	public abstract void saveAlbum(Album album) throws PersistanceManagerException;

	public List<ImageBean> getImageBeanList(String albumKeyString) throws PersistanceManagerException;
	
	public Data getImageData(String keyString) throws PersistanceManagerException;
	
	public void deleteImageFile(String key) throws PersistanceManagerException;
	
	public Image getImage(String keyString) throws PersistanceManagerException;
	public ImageBean getImageBean(String keyString) throws PersistanceManagerException;
}