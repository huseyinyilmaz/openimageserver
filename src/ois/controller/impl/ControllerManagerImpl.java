package ois.controller.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import javax.jdo.PersistenceManager;

import ois.ApplicationManager;
import ois.controller.Album;
import ois.controller.ControllerManager;
import ois.controller.Image;
import ois.exceptions.PersistanceManagerException;
import ois.model.AlbumFile;
import ois.model.ImageData;
import ois.model.ImageFile;
import ois.model.ModelManager;
import ois.model.PMF;
import ois.view.ImageLink;

import com.google.appengine.api.datastore.Blob;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

public class ControllerManagerImpl implements ControllerManager{
	private static final Logger log = Logger.getLogger(ControllerManagerImpl.class.getName());
	private ModelManager modelManager;
	
	/* (non-Javadoc)
	 * @see ois.controller.ControllerManager#saveImage(ois.controller.Image)
	 */
	public void createImage(Image img) throws PersistanceManagerException{
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try{
			pm.currentTransaction().begin();
			Key albumFileKey = KeyFactory.stringToKey(img.getAlbum());
			//create new Image
			ImageFile imageFile = new ImageFile();
			imageFile.setName(img.getName());
			imageFile.setType(ois.model.ImageFileType.fromString(img.getType()) );
			imageFile.setAlbumFileKey(albumFileKey);
			imageFile.setDescription(img.getDescription());
			modelManager.saveImageFile(imageFile, pm);
			pm.currentTransaction().commit();//finish first transaction 
			imageFile = pm.detachCopy(imageFile);
			//pm.close();
			//pm = PMF.get().getPersistenceManager();
			pm.currentTransaction().begin();//start second transaction
			//create new data
			ImageData imageData = new ImageData(new Blob(img.getData()),imageFile.getType());
			imageData.setOriginal(true);
			imageData.setImageFileKey(imageFile.getKey());
			//TODO add image info
			modelManager.saveImageData(imageData, pm);
			pm.currentTransaction().commit();
		}finally{
			if(pm.currentTransaction().isActive())
				pm.currentTransaction().rollback();
			pm.close();
		}
	}
	
	/* (non-Javadoc)
	 * @see ois.controller.ControllerManager#getAlbums()
	 */
	public List<Album> getAlbums(){
		PersistenceManager pm = PMF.get().getPersistenceManager();
		List<Album> albums = new ArrayList<Album>();
		try{
			for(AlbumFile album: modelManager.getAlbums(pm))
				albums.add( new Album(KeyFactory.keyToString(album.getKey()),album.getName(),album.getDescription(),album.getCreationDate()));
		}finally{
			pm.close();
		}
		return albums;
	}
		
	
	/* (non-Javadoc)
	 * @see ois.controller.ControllerManager#createAlbum(java.lang.String, java.lang.String)
	 */
	public void createAlbum(String name, String description) throws PersistanceManagerException{
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try{
			pm.currentTransaction().begin();
			//TODO check if the name is unique
			if(!Pattern.matches("\\w+",name))
				throw new IllegalArgumentException("name can only be consist of digits , letters or _ characters");
			AlbumFile album = new AlbumFile(name,description);
			modelManager.saveAlbum(album,pm);
			pm.currentTransaction().commit();
		}finally{
			if(pm.currentTransaction().isActive())
				pm.currentTransaction().rollback();
			pm.close();
		}
	}

	public ControllerManagerImpl(ModelManager modelManager){
		this.modelManager = modelManager;
	}

	/* (non-Javadoc)
	 * @see ois.controller.ControllerManager#deleteAlbum(long)
	 */
	public void deleteAlbum(String key) throws PersistanceManagerException{
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try{
			pm.currentTransaction().begin();
			AlbumFile albumFile = modelManager.getAlbumFile(KeyFactory.stringToKey(key),pm);
			if (albumFile == null)
				throw new IllegalArgumentException("Album with key '" + key +" could not be found");
			modelManager.deleteAlbum(albumFile,pm);
			pm.currentTransaction().commit();
			}finally{
				if(pm.currentTransaction().isActive())
					pm.currentTransaction().rollback();
				pm.close();
			}
	}

	
	/* (non-Javadoc)
	 * @see ois.controller.ControllerManager#getAlbum(long)
	 */
	public Album getAlbum(String key) throws PersistanceManagerException {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		AlbumFile albumFile;
		try{
			albumFile = modelManager.getAlbumFile(KeyFactory.stringToKey(key),pm);
		}finally{
			pm.close();
		}
		return new Album(key,albumFile.getName(),albumFile.getDescription(),albumFile.getCreationDate());
	}

	
	/* (non-Javadoc)
	 * @see ois.controller.ControllerManager#saveAlbum(ois.controller.Album)
	 */
	public void saveAlbum(Album album) throws PersistanceManagerException {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try{
			pm.currentTransaction().begin();
			AlbumFile albumFile = modelManager.getAlbumFile(KeyFactory.stringToKey(album.getKey()),pm);
			albumFile.setName(album.getName());
			albumFile.setDescription(album.getDescription());
			modelManager.saveAlbum(albumFile,pm);
			pm.currentTransaction().commit();
		}finally{
			if(pm.currentTransaction().isActive())
				pm.currentTransaction().rollback();
			pm.close();
		}

	}

	/* (non-Javadoc)
	 * @see ois.controller.ControllerManager#getImageLinks(long)
	 */
	public List<ImageLink> getImageLinks(String albumKey) throws PersistanceManagerException {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		List<ImageLink> images = new ArrayList<ImageLink>();
		try{
			//if key of album is "none" then we just return an empty list.
			if(albumKey.equals(ApplicationManager.ALBUMNODE_NONE))
				images = Collections.emptyList();
			else{
			//list that will hold result image links
				Iterable<ImageFile> imageFiles;
				if(albumKey.equals(ApplicationManager.ALBUMNODE_ALL))
					//get all the images in db
					imageFiles = modelManager.getAllImages(pm);
				else
					//get images only given album contains
					imageFiles = modelManager.getImageFilesByAlbum(KeyFactory.stringToKey(albumKey),pm);

				for( ImageFile imageFile : imageFiles ){
					log.info("Create link for image file " + imageFile.getKey());
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
			}
		}finally{
			pm.close();
		}
		return images;
	}		

}
