package ois.controller.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ois.ApplicationManager;
import ois.controller.Album;
import ois.controller.ControllerManager;
import ois.controller.Data;
import ois.controller.Image;
import ois.exceptions.DeleteSystemRevisionException;
import ois.exceptions.EmptyImageDataException;
import ois.exceptions.ImageDataTooBigException;
import ois.exceptions.InvalidNameException;
import ois.exceptions.PersistanceManagerException;
import ois.model.AlbumFile;
import ois.model.ImageData;
import ois.model.ImageFile;
import ois.model.ImageType;
import ois.model.ModelManager;
import ois.model.PMF;
import ois.view.AlbumBean;
import ois.view.CSParamType;
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
	
	public void createImage(Image img) throws PersistanceManagerException, InvalidNameException, EmptyImageDataException, ImageDataTooBigException{
		//check type from image service.
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try{
			//create new ImageFile from given Image object
			ImageFile imageFile = toImageFile(img);
			imageFile.setCreationDate(new Date());
			ApplicationManager.checkName(img.getName());
			if(modelManager.getImageFileByName(img.getName(),imageFile.getAlbumFileKey(),pm)!=null)
				throw new InvalidNameException("An image with name '" + img.getName() + "' is already exist");
			if (img.getDataList().isEmpty() || img.getDataList().get(0).getData().length==0)
				throw new EmptyImageDataException("Please choose an image file to upload");
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
			try{
				modelManager.saveImageData(imageData, pm);
			}catch(ImageDataTooBigException e){
				deleteImageFile(KeyFactory.keyToString(imageFile.getKey()));
				throw e;
			}
			
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
		
	
	public String createAlbum(String name, String description) 
				throws PersistanceManagerException, InvalidNameException{
		PersistenceManager pm = PMF.get().getPersistenceManager();
		AlbumFile albumFile;
		try{
			ApplicationManager.checkName(name);
			//check if the name is unique
			if(modelManager.getAlbumFileByName(name,pm)!= null)
				throw new InvalidNameException("An album with name '" + name + "' is already exists.");
			albumFile = new AlbumFile(name,description);
			albumFile.setCreationDate(new Date());
			modelManager.saveAlbum(albumFile,pm);
		}finally{
			pm.close();
		}
		return KeyFactory.keyToString(albumFile.getKey());
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

	public void deleteImageData(String key) throws PersistanceManagerException, DeleteSystemRevisionException{
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

	
	public void saveAlbum(Album album) throws PersistanceManagerException, InvalidNameException {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try{
			ApplicationManager.checkName(album.getName());
			//check if the name is unique
			AlbumFile duplicatedNameAlbumFile = modelManager.getAlbumFileByName(album.getName(),pm);
			if(duplicatedNameAlbumFile != null
			&& !KeyFactory.keyToString(duplicatedNameAlbumFile.getKey()).equals(album.getKeyString()))
				throw new InvalidNameException("An album with name '" + album.getName() + "' is already exists.");
			AlbumFile albumFile = modelManager.getAlbumFile(KeyFactory.stringToKey(album.getKeyString()),pm);
			albumFile.setName(album.getName());
			albumFile.setDescription(album.getDescription());
			modelManager.saveAlbum(albumFile,pm);
		}finally{
			pm.close();
		}

	}
	
	@Override
	public void saveImage(Image image) throws PersistanceManagerException, InvalidNameException {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		ImageFile imageFile = modelManager.getImageFile(KeyFactory.stringToKey(image.getKeyString()), pm);
		try{
			ApplicationManager.checkName(image.getName());
			ImageFile duplicatedNamedImage = modelManager.getImageFileByName(image.getName(),imageFile.getAlbumFileKey(),pm);
			if(duplicatedNamedImage!= null && duplicatedNamedImage.getKey() != imageFile.getKey())
				throw new InvalidNameException("An image with name '" + image.getName() + "' is already exist");
			imageFile.setName(image.getName());
			imageFile.setDescription(image.getDescription());
			modelManager.saveImageFile(imageFile, pm);
		}finally{
			pm.close();
		}
	}

	@Override
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

	public DataBean getOriginalDataBean(String imageFilekeyString) throws PersistanceManagerException{
		PersistenceManager pm = PMF.get().getPersistenceManager();
		DataBean dataBean;
		try{
			ImageData imageData = modelManager.getOriginal(KeyFactory.stringToKey(imageFilekeyString), pm);
			dataBean = toDataBean(imageData);
		}finally{
			pm.close();
		}
		return dataBean;
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

	public AlbumBean getImageBean(String keyString) throws PersistanceManagerException{
		PersistenceManager pm = PMF.get().getPersistenceManager();
		AlbumBean albumBean;
		ImageBean imageBean;
		try{
			Key imageFileKey = KeyFactory.stringToKey(keyString);
			ImageFile imageFile = modelManager.getImageFile(imageFileKey, pm);
			AlbumFile albumFile = modelManager.getAlbumFile(imageFile.getAlbumFileKey(), pm);
			albumBean = toAlbumBean(albumFile);
			albumBean.setCurrentImageKeyString(keyString);
			imageBean = toImageBean(imageFile);
			imageBean.setDataBeanList(new ArrayList<DataBean>());
			List<ImageData> imageDataList = modelManager.getImageDataByImageFile(imageFileKey, pm);
			for(ImageData imageData:imageDataList){
				DataBean dataBean = toDataBean(imageData);
				dataBean.setImageKeyString(keyString);
				imageBean.getDataBeanList().add(dataBean);
			}
			albumBean.setImageBeanList(new ArrayList<ImageBean>());
			albumBean.getImageBeanList().add(imageBean);			
		}finally{
			pm.close();
		}
		return albumBean;
	}

	private void forward(String context, HttpServlet servlet, HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
		servlet.getServletContext().getRequestDispatcher(context).forward(req, res); 
	}

	public void initImageCreate(HttpServlet servlet,HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
		if(req.getAttribute("exception")== null){
			//clean state
			String albumKeyString = req.getParameter(CSParamType.ALBUM.toString());
			AlbumBean albumBean = new AlbumBean();
			albumBean.setKeyString(albumKeyString);
			req.setAttribute("albumBean",albumBean);
			log.info("Image create page is being opened for album " + albumKeyString );
		}
		forward(ApplicationManager.JSP_IMAGE_CREATE_URL,servlet,req,res);
	}
	
	public String createImageData(String imageFileKeyString, Data infoData) throws PersistanceManagerException, ImageDataTooBigException{
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Key imageFileKey = KeyFactory.stringToKey(imageFileKeyString);
		
		ImageData originalData = modelManager.getOriginal(imageFileKey,pm);
		Data data = new Data();
		data.setData(originalData.getData().getBytes());
		if(infoData.isEnhanced())
			data = ApplicationManager.getManipulator().resizeAndEnhance(data, infoData.getWidth(), infoData.getHeight());
		else
			data = ApplicationManager.getManipulator().resize(data, infoData.getWidth(), infoData.getHeight());
		
		ImageData duplicatedImageData = modelManager.getImageDataByProperties(data.getWidth(), data.getHeight(), data.isEnhanced(), imageFileKey, pm);
		//if revision with given properties is exist return already exited
		//revision. Do not create new one.
		if (duplicatedImageData != null)
			return KeyFactory.keyToString(duplicatedImageData.getKey());

		ImageData imageData = toImageData(data);
		imageData.setImageFileKey(imageFileKey);
		imageData.setCreationDate(new Date());
		try{
			modelManager.saveImageData(imageData, pm);
		}finally{
			pm.close();
		}
		return KeyFactory.keyToString(imageData.getKey());
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
