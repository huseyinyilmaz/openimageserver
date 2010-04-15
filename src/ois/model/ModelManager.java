package ois.model;

import ois.exceptions.PersistanceManagerException;

public interface ModelManager {

	public abstract void saveImageFile(ImageFile file)
			throws PersistanceManagerException;

	public abstract Iterable<AlbumFile> getAlbums();

	public abstract ImageFile getImage(String location, String name);

	public abstract ImageFileType getImageType(String typeString);

	public abstract void saveAlbum(AlbumFile album)
			throws PersistanceManagerException;
	
	//TODO burda exception firlatilmiyorsa kaldir exception mesajini
	public abstract AlbumFile getAlbum(long id)
			throws PersistanceManagerException;

	public abstract void deleteAlbum(AlbumFile album)
			throws PersistanceManagerException;

	public abstract String getImageLink(long id,String extension);
	
	public abstract Iterable<ImageFile> getImages();

	public abstract void createImage(ImageFile file)
			throws PersistanceManagerException;
	
	public ImageFile getImageFile(long id)
			throws PersistanceManagerException;
}