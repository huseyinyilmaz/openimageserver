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
import com.google.appengine.api.datastore.KeyFactory;

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
	public ImageFile getImageFile(Key key,PersistenceManager pm) throws PersistanceManagerException {
		return pm.getObjectById(ImageFile.class, key);
	}

	/* (non-Javadoc)
	 * @see ois.model.ModelManager#getAlbum(long)
	 */
	public ImageData getImageData(Key key,PersistenceManager pm) throws PersistanceManagerException {
		return pm.getObjectById(ImageData.class, key);
	}

	/* (non-Javadoc)
	 * @see ois.model.ModelManager#deleteAlbum(ois.model.AlbumFile)
	 */
	public void deleteAlbum(AlbumFile album,PersistenceManager pm) throws PersistanceManagerException {
        try {
        	pm.deletePersistent(album);
        }catch(Exception e){
        	PersistanceManagerException pme = new PersistanceManagerException("Album [" + album.getName() + "] could not be deleted",e);
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
	
	public ImageData getThumbnail(Key imageFileKey,PersistenceManager pm){
/*
		String query = "select from " + ImageData.class.getName() +
					" where imageFileKey=="+ KeyFactory.keyToString(imageFileKey);
		List<ImageData> imageDatas = (List<ImageData>)pm.newQuery(query).execute();
		*/
		Query query = pm.newQuery(ImageData.class);
		query.setFilter("imageFileKey == imageFileKeyParam && isOriginal == true");
		query.declareParameters("com.google.appengine.api.datastore.Key imageFileKeyParam");
		List<ImageData> imageDatas = (List<ImageData>) query.execute(imageFileKey);
		
		return imageDatas.get(0);
	}
	
}
