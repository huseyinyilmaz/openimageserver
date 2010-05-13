package ois.model;

import java.util.List;

import javax.jdo.PersistenceManager;

import com.google.appengine.api.datastore.Key;

import ois.exceptions.PersistanceManagerException;

public interface ModelManager {

	public abstract void saveImageFile(ImageFile file,PersistenceManager pm)
			throws PersistanceManagerException;

	public abstract void saveImageData(ImageData imageData,PersistenceManager pm)
	throws PersistanceManagerException;

	public abstract Iterable<AlbumFile> getAlbums(PersistenceManager pm);

	public abstract void saveAlbum(AlbumFile album,PersistenceManager pm)
			throws PersistanceManagerException;
	
	public abstract Iterable<ImageFile> getAllImages(PersistenceManager pm);

	
	public abstract List<ImageFile> getImageFilesByAlbum(Key albumKey,PersistenceManager pm); 
	public abstract List<ImageData> getImageDataByImageFile(Key imageFileKey,PersistenceManager pm);
	
	public abstract ImageData getImageData(Key key,PersistenceManager pm);
	public abstract ImageFile getImageFile(Key key,PersistenceManager pm);
	public abstract AlbumFile getAlbumFile(Key key,PersistenceManager pm);
	
	
	public abstract ImageData getThumbnail(Key imageFileKey,PersistenceManager pm);
	public abstract ImageData getOriginal(Key imageFileKey, PersistenceManager pm);
	
	public abstract void deleteAlbumFile(Key key,PersistenceManager pm)throws PersistanceManagerException;
	public abstract void deleteImageFile(Key key, PersistenceManager pm) throws PersistanceManagerException;
	public abstract void deleteImageFile(ImageFile imageFile, PersistenceManager pm) throws PersistanceManagerException;
	public abstract void deleteImageData(Key key,PersistenceManager pm)throws PersistanceManagerException;
	public abstract void deleteImageData(ImageData imageData, PersistenceManager pm) throws PersistanceManagerException;



}