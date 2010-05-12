package ois.controller.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
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
import ois.model.ImageType;
import ois.model.ModelManager;
import ois.model.PMF;
import ois.view.AlbumBean;
import ois.view.DataBean;
import ois.view.ImageBean;

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
			imageFile.setCreationDate(new Date());
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
			imageData.setCreationDate(new Date());
			imageData.setImageFileKey(imageFile.getKey());
			//save original image data.
			modelManager.saveImageData(imageData, pm);
			//create new thumbnail
			Data thumbnailRawData = ApplicationManager.getManipulator().resizeAndEnhance(data, 100, 100);
			thumbnailRawData.setThumbnail(true);
			thumbnailRawData.setOriginal(false);//this object is created from original one
			ImageData thumbnailData = toImageData(thumbnailRawData);
			thumbnailData.setImageFileKey(imageFile.getKey());
			thumbnailData.setCreationDate(new Date());
			modelManager.saveImageData(thumbnailData, pm);
		}finally{
			pm.close();
		}
	}
	
	public List<AlbumBean> getAlbumBeanList(){
		PersistenceManager pm = PMF.get().getPersistenceManager();
		List<AlbumBean> albumBeanList = new ArrayList<AlbumBean>();
		try{
			for(AlbumFile albumFile: modelManager.getAlbums(pm)){
				albumBeanList.add(toAlbumBean(albumFile));
			}
		}finally{
			pm.close();
		}
		return albumBeanList;
	}
		
	
	public void createAlbum(String name, String description) throws PersistanceManagerException{
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try{
			pm.currentTransaction().begin();
			//TODO check if the name is unique
			if(!Pattern.matches("\\w+",name))
				throw new IllegalArgumentException("name can only be consist of digits , letters or _ characters");
			AlbumFile album = new AlbumFile(name,description);
			album.setCreationDate(new Date());
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

	
	public Album getAlbum(String keyString) throws PersistanceManagerException {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		AlbumFile albumFile;
		try{
			albumFile = modelManager.getAlbumFile(KeyFactory.stringToKey(keyString),pm);
		}finally{
			pm.close();
		}
		return new Album(keyString,albumFile.getName(),albumFile.getDescription(),albumFile.getCreationDate());
	}

	public AlbumBean getAlbumBean(String keyString) throws PersistanceManagerException {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		AlbumFile albumFile;
		try{
			albumFile = modelManager.getAlbumFile(KeyFactory.stringToKey(keyString),pm);
		}finally{
			pm.close();
		}
		return toAlbumBean(albumFile);
	}

	
	public void saveAlbum(Album album) throws PersistanceManagerException {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try{
			AlbumFile albumFile = modelManager.getAlbumFile(KeyFactory.stringToKey(album.getKeyString()),pm);
			albumFile.setName(album.getName());
			albumFile.setDescription(album.getDescription());
			modelManager.saveAlbum(albumFile,pm);
		}finally{
			pm.close();
		}

	}

	public List<ImageBean> getImageBeanList(String albumKeyString) throws PersistanceManagerException {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		List<ImageBean> imageBeanList = new ArrayList<ImageBean>();
		try{
			//if key of album is "none" then we just return an empty list.
			if(albumKeyString.equals(ApplicationManager.NONE))
				imageBeanList = Collections.emptyList();
			else{
			//list that will hold result image links
				Iterable<ImageFile> imageFiles = modelManager.getImageFilesByAlbum(KeyFactory.stringToKey(albumKeyString),pm);
				for( ImageFile imageFile : imageFiles ){
					//TODO is flush necessary
					pm.flush();
					log.info("Create link for image file " + imageFile.getKey());
					//create an image and set properties
					ImageBean imageBean = toImageBean(imageFile);
					ImageData imageData = modelManager.getThumbnail(imageFile.getKey(), pm);
					log.info("Image data for "+ imageFile.getKey() + " is " + imageData.getKey());
					List<DataBean> dataBeanList = new ArrayList<DataBean>();
					dataBeanList.add(toDataBean(imageData));
					imageBean.setDataBeanList(dataBeanList);
					imageBeanList.add(imageBean);
				}
			}
		}finally{
			pm.close();
		}
		return imageBeanList;
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
	
	public Image getImage(String keyString) throws PersistanceManagerException{
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Image image;
		try{
			Key imageFileKey = KeyFactory.stringToKey(keyString);
			ImageFile imageFile = modelManager.getImageFile(imageFileKey, pm);
			image = toImage(imageFile);
			
			List<ImageData> imageDataList = modelManager.getImageDataByImageFile(imageFileKey, pm);
			for(ImageData imageData:imageDataList){
				Data data = toData(imageData);
				image.getDataList().add(data);
			}
		}finally{
			pm.close();
		}
		return image;
	}

	public ImageBean getImageBean(String keyString) throws PersistanceManagerException{
		PersistenceManager pm = PMF.get().getPersistenceManager();
		ImageBean imageBean;
		try{
			Key imageFileKey = KeyFactory.stringToKey(keyString);
			ImageFile imageFile = modelManager.getImageFile(imageFileKey, pm);
			imageBean = toImageBean(imageFile);
			imageBean.setDataBeanList(new ArrayList<DataBean>());
			List<ImageData> imageDataList = modelManager.getImageDataByImageFile(imageFileKey, pm);
			for(ImageData imageData:imageDataList){
				DataBean dataBean = toDataBean(imageData);
				dataBean.setImageKeyString(keyString);
				imageBean.getDataBeanList().add(dataBean);
			}
		}finally{
			pm.close();
		}
		return imageBean;
	}

	public void createImageData(String imageFileKeyString, Data infoData) throws PersistanceManagerException{
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Key imageFileKey = KeyFactory.stringToKey(imageFileKeyString);
		//ImageFile imageFile = modelManager.getImageFile(imageFileKey, pm);
		ImageData originalData = modelManager.getOriginal(imageFileKey,pm);
		Data data = new Data();
		data.setData(originalData.getData().getBytes());
		if(infoData.isEnhanced())
			data = ApplicationManager.getManipulator().resizeAndEnhance(data, infoData.getWidth(), infoData.getHeight());
		else
			data = ApplicationManager.getManipulator().resize(data, infoData.getWidth(), infoData.getHeight());
		
		ImageData imageData = toImageData(data);
		imageData.setType(originalData.getType());
		imageData.setImageFileKey(imageFileKey);
		imageData.setCreationDate(new Date());
		try{
			modelManager.saveImageData(imageData, pm);
		}finally{
			pm.close();
		}
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
		if(data.getData() != null)
			imageData.setData(new Blob(data.getData()));
		imageData.setType(ImageType.fromString(data.getType()));
		imageData.setCreationDate(data.getCreationDate());
		imageData.setOriginal(data.isOriginal());
		imageData.setThumbnail(data.isThumbnail());
		imageData.setEnhanced(data.isEnhanced());
		imageData.setWidth(data.getWidth());
		imageData.setHeight(data.getHeight());
		return imageData;
	}

	private ImageFile toImageFile(Image image){
		ImageFile imageFile = new ImageFile();
		imageFile.setCreationDate(image.getCreationDate());
		imageFile.setName(image.getName());
		imageFile.setDescription(image.getDescription());
		if(image.getKeyString() != null)
			imageFile.setKey(KeyFactory.stringToKey(image.getKeyString()));
		imageFile.setType(ImageType.fromString(image.getType()));
		if(image.getAlbum() != null)
			imageFile.setAlbumFileKey(KeyFactory.stringToKey(image.getAlbum()));
		return imageFile;
	}
	
	private Image toImage(ImageFile imageFile){
		Image image = new Image();
		image.setCreationDate(imageFile.getCreationDate());
		image.setName(imageFile.getName());
		image.setDescription(imageFile.getDescription());
		image.setAlbum(KeyFactory.keyToString(imageFile.getAlbumFileKey()));
		image.setType(imageFile.getType().toString());
		image.setKeyString(KeyFactory.keyToString(imageFile.getKey()));
		return image;
	}
	private ImageBean toImageBean(ImageFile imageFile){
		ImageBean imageBean = new ImageBean();
		imageBean.setCreationDate(imageFile.getCreationDate());
		imageBean.setDescription(imageFile.getDescription());
		imageBean.setKeyString(KeyFactory.keyToString(imageFile.getKey()));
		imageBean.setName(imageFile.getName());
		return imageBean;
	}
	private DataBean toDataBean(ImageData imageData){
		DataBean dataBean = new DataBean();
		dataBean.setCreationDate(imageData.getCreationDate());
		if( imageData.getKey() != null)
			dataBean.setKeyString(KeyFactory.keyToString(imageData.getKey()));
		dataBean.setEnhanced(imageData.isEnhanced());
		dataBean.setOriginal(imageData.isOriginal());
		dataBean.setThumbnail(imageData.isThumbnail());
		dataBean.setHeight(imageData.getHeight());
		dataBean.setWidth(imageData.getWidth());
		dataBean.setTypeString(imageData.getType().toString());
		return dataBean;
	}
	private AlbumBean toAlbumBean(AlbumFile albumFile){
		AlbumBean albumBean = new AlbumBean();
		if(albumFile.getKey() != null)
			albumBean.setKeyString(KeyFactory.keyToString(albumFile.getKey()));
		albumBean.setName(albumFile.getName());
		albumBean.setDescription(albumFile.getDescription());
		return albumBean;
	}
	
}
