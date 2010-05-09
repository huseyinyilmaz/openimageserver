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
import ois.controller.Data;
import ois.controller.Image;
import ois.exceptions.PersistanceManagerException;
import ois.model.AlbumFile;
import ois.model.ImageData;
import ois.model.ImageFile;
import ois.model.ImageFileType;
import ois.model.ModelManager;
import ois.model.PMF;
import ois.view.ImageLink;

import com.google.appengine.api.datastore.Blob;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

public class ControllerManagerImpl implements ControllerManager{
	private static final Logger log = Logger.getLogger(ControllerManagerImpl.class.getName());
	private ModelManager modelManager;

	public ControllerManagerImpl(ModelManager modelManager){
		this.modelManager = modelManager;
	}
	
	public void createImage(Image img) throws PersistanceManagerException{
		//check type from image service.
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try{
			//create new ImageFile from given Image object
			ImageFile imageFile = toImageFile(img);
			modelManager.saveImageFile(imageFile, pm);
			//We cannot work on imageFile and image data in same transaction 
			imageFile = pm.detachCopy(imageFile);
			//create new data
			Data data = new Data(img.getDataList().get(0));
			//set image Properties
			ApplicationManager.getManipulator().setImageProperties(data);
			data.setOriginal(true);
			//convert controller Object to model object
			ImageData imageData = toImageData(data);
			imageData.setImageFileKey(imageFile.getKey());
			//save original image data.
			modelManager.saveImageData(imageData, pm);
			//create new thumbnail
			Data thumbnailRawData = ApplicationManager.getManipulator().resizeAndEnhance(data, 100, 100);
			thumbnailRawData.setThumbnail(true);
			thumbnailRawData.setOriginal(false);//this object is created from original one
			ImageData thumbnailData = toImageData(thumbnailRawData);
			thumbnailData.setImageFileKey(imageFile.getKey());
			modelManager.saveImageData(thumbnailData, pm);
		}finally{
			pm.close();
		}
	}
	
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

	public void deleteAlbum(String key) throws PersistanceManagerException{
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try{
			Key albumFileKey = KeyFactory.stringToKey(key);
			if (albumFileKey == null)
				throw new IllegalArgumentException("Album with key '" + albumFileKey +" could not be found");
			//Get all image files belongs to this album file
			List<ImageFile> imageFileList = modelManager.getImageFilesByAlbum(albumFileKey, pm);
			//Delete all image files belong to this album file
			for(ImageFile imageFile:imageFileList)
				deleteImageFile(imageFile,pm);
			//Delete album
			modelManager.deleteAlbumFile(albumFileKey,pm);
		}finally{
			pm.close();
		}
	}

	private void deleteImageFile(ImageFile imageFile,PersistenceManager pm) throws PersistanceManagerException{
		//Get all image data belongs to this image file
		List<ImageData> imageDataList = modelManager.getImageDataByImageFile(imageFile.getKey(), pm);
		//Delete all image data belong to this image file
		for(ImageData imageData:imageDataList)
			modelManager.deleteImageData(imageData, pm);
		//Delete image file
		modelManager.deleteImageFile(imageFile,pm);
	}
	
	public void deleteImageFile(String key) throws PersistanceManagerException{
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try{
			Key imageFileKey = KeyFactory.stringToKey(key);
			if (imageFileKey == null)
				throw new IllegalArgumentException("Image with key '" + imageFileKey +" could not be found");
			ImageFile imageFile = modelManager.getImageFile(imageFileKey, pm);
			deleteImageFile(imageFile, pm);
		}finally{
			pm.close();
		}
	}

	public void deleteImageData(String key) throws PersistanceManagerException{
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try{
			Key imageDataKey = KeyFactory.stringToKey(key);
			if (imageDataKey == null)
				throw new IllegalArgumentException("Image with key '" + imageDataKey +" could not be found");
			modelManager.deleteImageData(imageDataKey,pm);
		}finally{
			pm.close();
		}
	}

	
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
					pm.flush();
					log.info("Create link for image file " + imageFile.getKey());
					//create an image and set properties
					ImageLink imageLink = new ImageLink();
					imageLink.setCreationDate(imageFile.getCreationDate());
					imageLink.setDescription(imageFile.getDescription());
					imageLink.setKey(KeyFactory.keyToString(imageFile.getKey()));
					imageLink.setName(imageLink.getName());
					ImageData imageData;
					imageData = modelManager.getThumbnail(imageFile.getKey(), pm);
					log.info("Image data for "+ imageFile.getKey() + " is " + imageData.getKey());
					imageLink.setLink(modelManager.getImageLink(KeyFactory.keyToString(imageData.getKey()),imageFile.getType().getExtension()));
					//add image to image list.
					images.add(imageLink);
				}
			}
		}finally{
			pm.close();
		}
		return images;
	}		
	
	public Data getImageData(String keyString) throws PersistanceManagerException{
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Data data;
		try{
			ImageData imageData = modelManager.getImageData(KeyFactory.stringToKey(keyString), pm);
			data = toData(imageData);
		}finally{
			pm.close();
		}
		return data;
	}
	
	public Image getImageFile(String keyString) throws PersistanceManagerException{
		return null;
	}
	
	private Data toData(ImageData imageData){
		Data data = new Data();
		data.setData(imageData.getData().getBytes());
		data.setType(imageData.getType().toString());
		data.setCreationDate(imageData.getCreationDate());
		data.setOriginal(imageData.isOriginal());
		data.setEnhanced(imageData.isEnhanced());
		data.setWidth(imageData.getWidth());
		data.setHeight(imageData.getHeight());
		data.setThumbnail(imageData.isThumbnail());
		return data;
	}
	private ImageData toImageData(Data data){
		ImageData imageData = new ImageData();
		imageData.setData(new Blob(data.getData()));
		imageData.setType(ImageFileType.fromString(data.getType()));
		//TODO should we put creation date here?
		imageData.setCreationDate(data.getCreationDate());
		imageData.setOriginal(data.isOriginal());
		imageData.setEnhanced(data.isEnhanced());
		imageData.setWidth(data.getWidth());
		imageData.setHeight(data.getHeight());
		imageData.setHeight(data.getHeight());
		return imageData;
	}

	private ImageFile toImageFile(Image image){
		ImageFile imageFile = new ImageFile();
		imageFile.setCreationDate(image.getCreationDate());
		imageFile.setName(image.getName());
		imageFile.setDescription(image.getDescription());
		imageFile.setKey(KeyFactory.stringToKey(image.getKeyString()));
		imageFile.setType(ImageFileType.fromString(image.getType()));
		imageFile.setAlbumFileKey(KeyFactory.stringToKey(image.getAlbum()));
		return imageFile;
	}
	
}
