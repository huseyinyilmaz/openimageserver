package ois.model.impl;

import java.util.List;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import ois.exceptions.DeleteSystemRevisionException;
import ois.exceptions.ImageDataTooBigException;
import ois.exceptions.PersistanceManagerException;
import ois.model.AlbumFile;
import ois.model.ImageData;
import ois.model.ImageFile;
import ois.model.ModelManager;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class ModelManagerImpl implements ModelManager {
	private static final Logger log = Logger.getLogger(ModelManagerImpl.class.getName());
	//private PersistenceManager pm;
	/* (non-Javadoc)
	 * @see ois.model.impl.ModelManager#saveImage(ois.model.ImageFile)
	 */
	public void saveImageFile(ImageFile imageFile, PersistenceManager pm) throws PersistanceManagerException{
		UserService userService = UserServiceFactory.getUserService();
		//create album
		if(imageFile.getKey() == null){
			//admin creating an image
			imageFile.setGlobal(userService.isUserAdmin());
		}else if(imageFile.isGlobal() && !userService.isUserAdmin()){
			//trying to change admin object with normal user. return
			pm.makeNontransactional(imageFile);
			return;
		}
		try {
            pm.makePersistent(imageFile);
        }catch(Exception e){
        	PersistanceManagerException pme = new PersistanceManagerException("Cannot save image file. Name = " + imageFile.getName() , e);
        	log.warning("new PersistanceManagerException was thrown");
        	throw pme;
        }
        log.info("new image file was successfully saved. name = " + imageFile.getName() +
        		", description = " + imageFile.getDescription());
	}

	/* (non-Javadoc)
	 * @see ois.model.impl.ModelManager#saveImage(ois.model.ImageFile)
	 */
	public void saveImageData(ImageData imageData, PersistenceManager pm) throws ImageDataTooBigException{
		UserService userService = UserServiceFactory.getUserService();
		//create album. edit album is handled in controller manager
		if(imageData.getKey() == null){
			//admin creating an album
			imageData.setGlobal(userService.isUserAdmin());
		}
		try {
            pm.makePersistent(imageData);
		}catch(Exception e){
        	ImageDataTooBigException exception = new ImageDataTooBigException("File is too large for datastore. (Limit is 1 MB)", e);
        	log.warning("new DatastoreFailureException was caught = " + e);
        	throw exception;
        }
        log.info("new Image data was successfully saved.");
	}

	
	/* (non-Javadoc)
	 * @see ois.model.impl.ModelManager#getAlbums()
	 */
	@SuppressWarnings("unchecked")
	public Iterable<AlbumFile> getAlbums(PersistenceManager pm){
		//Iterable<AlbumFile> albums = pm.getExtent(AlbumFile.class);
		//return albums;
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		
		Query query = pm.newQuery(AlbumFile.class);
		if(!userService.isUserAdmin()){
			query.setFilter("owner == ownerParam || owner == null");
			query.declareParameters("com.google.appengine.api.users.User ownerParam, int isAdminParam");
		}
		List<AlbumFile> albumFileList = (List<AlbumFile>) query.execute(user,userService.isUserAdmin()?1:0);
		return albumFileList;
	}
	
	/* (non-Javadoc)
	 * @see ois.model.impl.ModelManager#saveAlbum(ois.model.AlbumFile)
	 */
	public void saveAlbum(AlbumFile albumFile,PersistenceManager pm) throws PersistanceManagerException{
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		//create album. edit album is handled in controller manager
		if(albumFile.getKey() == null){
			//admin creating an album
			if(userService.isUserAdmin())
				user = null;
			albumFile.setOwner(user);
		}
		
		try {
			pm.makePersistent(albumFile);
        }catch(Exception e){
        	PersistanceManagerException pme = new PersistanceManagerException("Cannot save album. Name = " + albumFile.getName() , e);
        	throw pme;
        }
        log.info("new album was successfully saved. name = " + albumFile.getName() +
        		", location = " + albumFile.getDescription());
	}

	/* (non-Javadoc)
	 * @see ois.model.ModelManager#getAlbum(long)
	 */
	public AlbumFile getAlbumFile(Key key,PersistenceManager pm){
		return pm.getObjectById(AlbumFile.class, key);
	}

	/* (non-Javadoc)
	 * @see ois.model.ModelManager#getAlbum(long)
	 */
	public ImageFile getImageFile(Key key,PersistenceManager pm){
		return pm.getObjectById(ImageFile.class, key);
	}

	/* (non-Javadoc)
	 * @see ois.model.ModelManager#getAlbum(long)
	 */
	public ImageData getImageData(Key key,PersistenceManager pm){
		return pm.getObjectById(ImageData.class, key);
	}

	/* (non-Javadoc)
	 * @see ois.model.ModelManager#deleteAlbum(ois.model.AlbumFile)
	 */
	public void deleteAlbumFile(Key key,PersistenceManager pm) throws PersistanceManagerException {
        AlbumFile albumFile = getAlbumFile(key, pm);
		
		if(albumFile.getOwner()==null && !UserServiceFactory.getUserService().isUserAdmin())
			return;

        try {
        	pm.deletePersistent(albumFile);
        }catch(Exception e){
        	PersistanceManagerException pme = new PersistanceManagerException("Album [" + albumFile.getName() + "] could not be deleted",e);
        	throw pme;
        }
	}
	

	/* (non-Javadoc)
	 * @see ois.model.ModelManager#getImages()
	 */
	@Override
	public Iterable<ImageFile> getAllImages(PersistenceManager pm) {
		Iterable<ImageFile> images = pm.getExtent(ImageFile.class);
		return images;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ImageFile> getImageFilesByAlbum(Key albumKey,PersistenceManager pm) {
		Query query = pm.newQuery(ImageFile.class);
		query.setFilter("albumFileKey == albumKeyParam");
		query.declareParameters("com.google.appengine.api.datastore.Key albumKeyParam");
		List<ImageFile> ImageFiles = (List<ImageFile>) query.execute(albumKey);
		
		return ImageFiles;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ImageData> getImageDataByImageFile(Key imageFileKey,PersistenceManager pm) {
		Query query = pm.newQuery(ImageData.class);
		query.setFilter("imageFileKey == imageKeyParam");
		query.declareParameters("com.google.appengine.api.datastore.Key imageKeyParam");
		List<ImageData> imageData = (List<ImageData>) query.execute(imageFileKey);
		return imageData;
	}

	@Override
	@SuppressWarnings("unchecked")
	public ImageData getThumbnail(Key imageFileKey,PersistenceManager pm){
		Query query = pm.newQuery(ImageData.class);
		query.setFilter("imageFileKey == imageFileKeyParam && thumbnail == true");
		query.declareParameters("com.google.appengine.api.datastore.Key imageFileKeyParam");
		List<ImageData> imageData = (List<ImageData>) query.execute(imageFileKey);
		ImageData data = null;
		if (imageData.size()>0)
			data = imageData.get(0);
		else{
			//TODO throw exception or log
		}
		return data;
	}

	@Override
	@SuppressWarnings("unchecked")
	public ImageData getOriginal(Key imageFileKey,PersistenceManager pm){
		Query query = pm.newQuery(ImageData.class);
		query.setFilter("imageFileKey == imageFileKeyParam && original == true");
		query.declareParameters("com.google.appengine.api.datastore.Key imageFileKeyParam");
		List<ImageData> imageData = (List<ImageData>) query.execute(imageFileKey);
		ImageData data = null;
		if (imageData.size()>0)
			data = imageData.get(0);
		else{
			//TODO throw exception or log
		}
		return data;
	}

	@Override
	@SuppressWarnings("unchecked")
	public AlbumFile getAlbumFileByName(String name,PersistenceManager pm){
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		String filter = "name == nameParam";
		if(!userService.isUserAdmin()){
			//for regular users
			filter += " && (owner==ownerParam || owner==null)";
		}

		Query query = pm.newQuery(AlbumFile.class);
		query.setFilter(filter);
		query.declareParameters("String nameParam, com.google.appengine.api.users.User ownerParam");
		List<AlbumFile> albumFileList = (List<AlbumFile>) query.execute(name, user);
		AlbumFile albumFile = null;
		if (albumFileList.size()>0)
			albumFile = albumFileList.get(0);
		else{
			//TODO throw exception or log
		}
		return albumFile;
	}

	@Override
	@SuppressWarnings("unchecked")
	public ImageFile getImageFileByName(String name,Key albumFileKey,PersistenceManager pm){
		Query query = pm.newQuery(ImageFile.class);
		query.setFilter("name == nameParam && albumFileKey == albumKeyParam");
		query.declareParameters("String nameParam, com.google.appengine.api.datastore.Key albumKeyParam");
		//query.declareParameters("com.google.appengine.api.datastore.Key albumKeyParam");
		List<ImageFile> imageFileList = (List<ImageFile>) query.execute(name,albumFileKey);
		ImageFile imageFile = null;
		if (imageFileList.size()>0)
			imageFile = imageFileList.get(0);
		else{
			//TODO throw exception or log
		}
		return imageFile;
	}
	
	@Override
	public void deleteImageData(Key key, PersistenceManager pm) throws PersistanceManagerException, DeleteSystemRevisionException {
		ImageData imageData = getImageData(key, pm);
		if(imageData.isOriginal()||imageData.isThumbnail())
			throw new DeleteSystemRevisionException("Cannot delete Original revision or thumbnail revision");
		deleteImageData(imageData, pm);
	}
	@Override
	public void deleteImageData(ImageData imageData, PersistenceManager pm) throws PersistanceManagerException {
        
		if(imageData.isGlobal() && !UserServiceFactory.getUserService().isUserAdmin())
			return;

		try {
        	pm.deletePersistent(imageData);
        }catch(Exception e){
        	PersistanceManagerException pme = new PersistanceManagerException("Image data [" + imageData.getKey() + "] could not be deleted",e);
        	throw pme;
        }
	}

	@Override
	public void deleteImageFile(Key key, PersistenceManager pm) throws PersistanceManagerException {
		ImageFile imageFile = getImageFile(key, pm);
		deleteImageFile(imageFile, pm);
	}
	@Override
	public void deleteImageFile(ImageFile imageFile, PersistenceManager pm) throws PersistanceManagerException {
		
		if(imageFile.isGlobal() && !UserServiceFactory.getUserService().isUserAdmin())
			return;
		
		try {
        	pm.deletePersistent(imageFile);
        }catch(Exception e){
        	PersistanceManagerException pme = new PersistanceManagerException("Album [" + imageFile.getName() + "] could not be deleted",e);
        	throw pme;
        }
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public ImageData getImageDataByProperties(int width,int height,boolean isEnhanced, Key imageFileKey, PersistenceManager pm){
		Query query = pm.newQuery(ImageData.class);
		query.setFilter("imageFileKey == imageKeyParam && " +
						"width == "+ width + " && " +
						"height == "+ height +" && " +
						"enhanced == isEnhancedParam");
		query.declareParameters("com.google.appengine.api.datastore.Key imageKeyParam, boolean isEnhancedParam");
		List<ImageData> imageDataList = (List<ImageData>) query.execute(imageFileKey,isEnhanced);
		if(imageDataList.size()==0)
			return null;
		else
			return imageDataList.get(0);
	}
}
