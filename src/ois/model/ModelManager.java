package ois.model;

import java.util.List;

import ois.exceptions.PersistanceManagerException;

public interface ModelManager {

	public abstract void saveImageFile(ImageFile file)
			throws PersistanceManagerException;

	public abstract Iterable<AlbumFile> getAlbums();

	public abstract void saveAlbum(AlbumFile album)
			throws PersistanceManagerException;
	
	//TODO burda exception firlatilmiyorsa kaldir exception mesajini
	public abstract AlbumFile getAlbumFile(long id)
			throws PersistanceManagerException;

	public abstract void deleteAlbum(AlbumFile album)
			throws PersistanceManagerException;

	public abstract String getImageLink(long id,String extension);
	
	public abstract Iterable<ImageFile> getAllImages();

	public ImageFile getImageFile(long id)
			throws PersistanceManagerException;
	
	public abstract List<ImageFile> getImages(long albumId); 
	
	/**
	 * Closes Persistent manager.
	 */
	public abstract void close();
	
	public abstract void addImageToAlbum(ImageFile imageFile,long albumId) throws PersistanceManagerException;
	
	public abstract void addDataToImage(ImageData imagedata,long imageId) throws PersistanceManagerException;

	//void addDataToImage(ImageData imagedata, ImageFile imageFile)throws PersistanceManagerException;

}