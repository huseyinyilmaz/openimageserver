package ois.model.impl;

import java.util.List;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import ois.exceptions.PersistanceManagerException;
import ois.model.AlbumFile;
import ois.model.ImageData;
import ois.model.ImageFile;
import ois.model.ModelManager;

import com.google.appengine.api.datastore.Key;

public class ModelManagerImpl implements ModelManager {
	private static final Logger log = Logger.getLogger(ModelManagerImpl.class.getName());
	//private PersistenceManager pm;
	/* (non-Javadoc)
	 * @see ois.model.impl.ModelManager#saveImage(ois.model.ImageFile)
	 */
	public void saveImageFile(ImageFile imageFile, PersistenceManager pm) throws PersistanceManagerException{
		try {
            pm.makePersistent(imageFile);
        }catch(Exception e){
        	PersistanceManagerException pme = new PersistanceManagerException("Cannot save image file. Name = " + imageFile.getName() , e);
        	throw pme;
        }
        log.info("new image file was successfully saved. name = " + imageFile.getName() +
        		", description = " + imageFile.getDescription());
	}

	/* (non-Javadoc)
	 * @see ois.model.impl.ModelManager#saveImage(ois.model.ImageFile)
	 */
	public void saveImageData(ImageData imageData, PersistenceManager pm) throws PersistanceManagerException{
		try {
            pm.makePersistent(imageData);
        }catch(Exception e){
        	PersistanceManagerException pme = new PersistanceManagerException("Cannot save image data.", e);
        	throw pme;
        }
        log.info("new Image data was successfully saved.");
	}

	
	/* (non-Javadoc)
	 * @see ois.model.impl.ModelManager#getAlbums()
	 */
	public Iterable<AlbumFile> getAlbums(PersistenceManager pm){
		Iterable<AlbumFile> albums = pm.getExtent(AlbumFile.class);
		return albums;
	}
	
	/* (non-Javadoc)
	 * @see ois.model.impl.ModelManager#saveAlbum(ois.model.AlbumFile)
	 */
	public void saveAlbum(AlbumFile album,PersistenceManager pm) throws PersistanceManagerException{
		try {
			pm.makePersistent(album);
        }catch(Exception e){
        	PersistanceManagerException pme = new PersistanceManagerException("Cannot save album. Name = " + album.getName() , e);
        	throw pme;
        }
        log.info("new album was successfully saved. name = " + album.getName() +
        		", location = " + album.getDescription());
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
		try {
        	pm.deletePersistent(albumFile);
        }catch(Exception e){
        	PersistanceManagerException pme = new PersistanceManagerException("Album [" + albumFile.getName() + "] could not be deleted",e);
        	throw pme;
        }
	}
	
	/* (non-Javadoc)
	 * @see ois.model.ModelManager#getImageLink(long, java.lang.String)
	 */
	public String getImageLink(String keyString, String extension){
		//TODO move this to application manager
		//return "/ois/images/" + keyString + "." + extension;
		return "/ois/images/" + keyString; //+ "." + extension;
	}

	/* (non-Javadoc)
	 * @see ois.model.ModelManager#getImages()
	 */
	@Override
	public Iterable<ImageFile> getAllImages(PersistenceManager pm) {
		Iterable<ImageFile> images = pm.getExtent(ImageFile.class);
		return images;
	}

	@Override
	public List<ImageFile> getImageFilesByAlbum(Key albumKey,PersistenceManager pm) {
		Query query = pm.newQuery(ImageFile.class);
		query.setFilter("albumFileKey == albumKeyParam");
		query.declareParameters("com.google.appengine.api.datastore.Key albumKeyParam");
		List<ImageFile> ImageFiles = (List<ImageFile>) query.execute(albumKey);
		
		return ImageFiles;
	}

	@Override
	public List<ImageData> getImageDataByImageFile(Key imageFileKey,PersistenceManager pm) {
		Query query = pm.newQuery(ImageData.class);
		//TODO do not show thumbnail
		query.setFilter("imageFileKey == imageKeyParam");
		query.declareParameters("com.google.appengine.api.datastore.Key imageKeyParam");
		List<ImageData> imageData = (List<ImageData>) query.execute(imageFileKey);
		return imageData;
	}

	
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
	public void deleteImageData(Key key, PersistenceManager pm) throws PersistanceManagerException {
		ImageData imageData = getImageData(key, pm);
		deleteImageData(imageData, pm);
	}
	@Override
	public void deleteImageData(ImageData imageData, PersistenceManager pm) throws PersistanceManagerException {
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
		try {
        	pm.deletePersistent(imageFile);
        }catch(Exception e){
        	PersistanceManagerException pme = new PersistanceManagerException("Album [" + imageFile.getName() + "] could not be deleted",e);
        	throw pme;
        }
	}
	
}
