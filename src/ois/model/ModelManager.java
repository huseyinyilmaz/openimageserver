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
	
	//TODO burda exception firlatilmiyorsa kaldir exception mesajini
	public abstract AlbumFile getAlbumFile(Key key,PersistenceManager pm);

	public abstract void deleteAlbum(AlbumFile album,PersistenceManager pm)
			throws PersistanceManagerException;

	public abstract String getImageLink(String keyString,String extension);
	
	public abstract Iterable<ImageFile> getAllImages(PersistenceManager pm);

	public ImageFile getImageFile(Key key,PersistenceManager pm)
			throws PersistanceManagerException;
	
	public abstract List<ImageFile> getImageFilesByAlbum(Key albumKey,PersistenceManager pm); 
	
	public abstract ImageData getImageData(Key key,PersistenceManager pm) throws PersistanceManagerException;
	public ImageData getThumbnail(Key imageFileKey,PersistenceManager pm);
}