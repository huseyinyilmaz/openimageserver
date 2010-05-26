package ois.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ois.exceptions.EmptyImageDataException;
import ois.exceptions.ImageDataTooBigException;
import ois.exceptions.InvalidNameException;
import ois.exceptions.PersistanceManagerException;
import ois.view.AlbumBean;
import ois.view.DataBean;
import ois.view.ImageBean;

public interface ControllerManager {
	public abstract void createImage(Image img)
			throws PersistanceManagerException, InvalidNameException, EmptyImageDataException, ImageDataTooBigException;

	public abstract List<AlbumBean> getAlbumBeanList();

	public abstract String createAlbum(String name, String description)
			throws PersistanceManagerException, InvalidNameException;

	public abstract void deleteAlbum(String key)
			throws PersistanceManagerException;

	public abstract Album getAlbum(String key)
			throws PersistanceManagerException;
	
	public abstract AlbumBean getAlbumBean(String key) throws PersistanceManagerException;
	public DataBean getOriginalDataBean(String imageFilekeyString) throws PersistanceManagerException;
	
	public abstract void saveAlbum(Album album) throws PersistanceManagerException, InvalidNameException;

	public abstract List<ImageBean> getImageBeanList(String albumKeyString) throws PersistanceManagerException;
	
	public abstract Data getImageData(String keyString) throws PersistanceManagerException;
	
	public abstract void deleteImageFile(String key) throws PersistanceManagerException;
	public abstract void deleteImageData(String key) throws PersistanceManagerException;	
	public abstract Image getImage(String keyString) throws PersistanceManagerException;
	public abstract AlbumBean getImageBean(String keyString) throws PersistanceManagerException;
	public abstract String createImageData(String imageFileKeyString, Data data) throws PersistanceManagerException, ImageDataTooBigException;

	public void initImageCreate(HttpServlet servlet,HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException;
}

