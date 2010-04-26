package ois.model;

import java.util.List;

import com.google.appengine.api.datastore.Key;

import ois.exceptions.PersistanceManagerException;

public interface ModelManager {

	public abstract void saveImageFile(ImageFile file)
			throws PersistanceManagerException;

	public abstract Iterable<AlbumFile> getAlbums();

	public abstract void saveAlbum(AlbumFile album)
			throws PersistanceManagerException;
	
	//TODO burda exception firlatilmiyorsa kaldir exception mesajini
	public abstract AlbumFile getAlbumFile(Key key)
			throws PersistanceManagerException;

	public abstract void deleteAlbum(AlbumFile album)
			throws PersistanceManagerException;

	public abstract String getImageLink(String keyString,String extension);
	
	public abstract Iterable<ImageFile> getAllImages();

	public ImageFile getImageFile(Key key)
			throws PersistanceManagerException;
	
	public abstract List<ImageFile> getImageFilesByAlbum(Key albumKey); 
	
	/**
	 * Closes Persistent manager.
	 */
	public abstract void close();
	
	public abstract void addImageToAlbum(ImageFile imageFile,Key albumFileKey) throws PersistanceManagerException;
	
	public abstract void addDataToImage(ImageData imagedata,Key ImageFileKey) throws PersistanceManagerException;

	
}