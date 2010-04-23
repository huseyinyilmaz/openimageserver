package ois.controller.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import ois.ApplicationManager;
import ois.controller.Album;
import ois.controller.ControllerManager;
import ois.controller.Image;
import ois.exceptions.PersistanceManagerException;
import ois.model.AlbumFile;
import ois.model.ImageData;
import ois.model.ImageFile;
import ois.model.ModelManager;
import ois.view.ImageLink;

import com.google.appengine.api.datastore.Blob;
import com.google.appengine.api.datastore.KeyFactory;

public class ControllerManagerImpl implements ControllerManager{
	private static final Logger log = Logger.getLogger(ControllerManagerImpl.class.getName());
	private ModelManager modelManager;
	
	/* (non-Javadoc)
	 * @see ois.controller.ControllerManager#saveImage(ois.controller.Image)
	 */
	public void createImage(Image img) throws PersistanceManagerException{
		//if(img.getAlbum().trim().equals(""))
		//	throw new IllegalArgumentException("Location cannot be null or consist of only white spaces");

		//AlbumFile album = modelManager.getAlbumFile(img.getAlbum());
		
		//create new Image
		ImageFile imageFile = new ImageFile();
		imageFile.setName(img.getName());
		imageFile.setType(ois.model.ImageFileType.fromString(img.getType()) );
		//create album-image connection
		//imageFile.setAlbumId(album.getKey().getId());
		
		//modelManager.createImage(file);
		//modelManager.saveAlbum(album);
//		long id = imageFile.getKey().getId();
		//file = modelManager.getImageFile(id);
		
		//create new data
		ImageData data = new ImageData(new Blob(img.getData()),imageFile.getType());
		data.setType(imageFile.getType());
		//imageFile.getImageData().add(data);
		//modelManager.saveImageFile(imageFile);

		//modelManager.saveImageFile(file);
		
		modelManager.addImageToAlbum(imageFile, img.getAlbum());
		modelManager.addDataToImage(data, imageFile.getKey().getId());
		//modelManager.addDataToImage(data, imageFile);
	}
	
	/* (non-Javadoc)
	 * @see ois.controller.ControllerManager#getAlbums()
	 */
	public List<Album> getAlbums(){
		List<Album> albums = new ArrayList<Album>();
		for(AlbumFile album: modelManager.getAlbums()){
			albums.add( new Album(KeyFactory.keyToString(album.getKey()),album.getName(),album.getDescription(),album.getCreationDate()));
		}
		return albums;
	}
	
	/* (non-Javadoc)
	 * @see ois.controller.ControllerManager#createAlbum(java.lang.String, java.lang.String)
	 */
	public void createAlbum(String name, String description) throws PersistanceManagerException{
		
		//TODO check if the name is unique
		if(!Pattern.matches("\\w+",name))
			throw new IllegalArgumentException("name can only be consist of digits , letters or _ characters");
		AlbumFile album = new AlbumFile(name,description);
		modelManager.saveAlbum(album);
	}

	public ControllerManagerImpl(ModelManager modelManager){
		this.modelManager = modelManager;
	}

	/* (non-Javadoc)
	 * @see ois.controller.ControllerManager#deleteAlbum(long)
	 */
	public void deleteAlbum(String key) throws PersistanceManagerException{
		AlbumFile albumFile = modelManager.getAlbumFile(KeyFactory.stringToKey(key));
		if (albumFile == null)
			throw new IllegalArgumentException("Album with key '" + key +" could not be found");
		modelManager.deleteAlbum(albumFile);
	}

	
	/* (non-Javadoc)
	 * @see ois.controller.ControllerManager#getAlbum(long)
	 */
	public Album getAlbum(String key) throws PersistanceManagerException {
		AlbumFile albumFile = modelManager.getAlbumFile(KeyFactory.stringToKey(key));
		return new Album(key,albumFile.getName(),albumFile.getDescription(),albumFile.getCreationDate());
		
	}

	
	/* (non-Javadoc)
	 * @see ois.controller.ControllerManager#saveAlbum(ois.controller.Album)
	 */
	public void saveAlbum(Album album) throws PersistanceManagerException {
		AlbumFile albumFile = modelManager.getAlbumFile(KeyFactory.stringToKey(album.getKey()));
		albumFile.setName(album.getName());
		albumFile.setDescription(album.getDescription());
		modelManager.saveAlbum(albumFile);
	}

	/* (non-Javadoc)
	 * @see ois.controller.ControllerManager#getImageLinks(long)
	 */
	public List<ImageLink> getImageLinks(String albumKey) throws PersistanceManagerException {
		//if key of album is "none" then we just return an empty list.
		if(albumKey.equals(ApplicationManager.ALBUMNODE_NONE))
			return Collections.emptyList();
		//list that will hold result image links
		List<ImageLink> images = new ArrayList<ImageLink>();
		Iterable<ImageFile> imageFiles;
		if(albumKey.equals(ApplicationManager.ALBUMNODE_ALL))
			//get all the images in db
			imageFiles = modelManager.getAllImages();
		else
			//get images only given album contains
			imageFiles = modelManager.getImageFilesByAlbum(KeyFactory.stringToKey(albumKey));
		
		for( ImageFile imageFile : imageFiles ){
			//create an image and set properties
			ImageLink imageLink = new ImageLink();
			imageLink.setCreationDate(imageFile.getCreationDate());
			imageLink.setDescription(imageFile.getDescription());
			imageLink.setKey(KeyFactory.keyToString(imageFile.getKey()));
			imageLink.setName(imageLink.getName());
			imageLink.setLink(modelManager.getImageLink(imageLink.getKey(),imageFile.getType().getExtension()));
			//add image to image list.
			images.add(imageLink);
		}
		return images;
	}

	@Override
	public void close() {
		modelManager.close();
	}

}
