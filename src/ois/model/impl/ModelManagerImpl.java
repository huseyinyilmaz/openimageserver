package ois.model.impl;

import java.util.List;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;

import ois.exceptions.PersistanceManagerException;
import ois.model.AlbumFile;
import ois.model.ImageData;
import ois.model.ImageFile;
import ois.model.ModelManager;
import ois.model.PMF;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

public class ModelManagerImpl implements ModelManager {
	private static final Logger log = Logger.getLogger(ModelManagerImpl.class.getName());
	private PersistenceManager pm;
	/* (non-Javadoc)
	 * @see ois.model.impl.ModelManager#saveImage(ois.model.ImageFile)
	 */
	public void saveImageFile(ImageFile imageFile) throws PersistanceManagerException{
		open();
		try {
            pm.makePersistent(imageFile);
        }catch(Exception e){
        	PersistanceManagerException pme = new PersistanceManagerException("Cannot save album. Name = " + imageFile.getName() , e);
        	throw pme;
        } finally {
        	if(pm.currentTransaction().isActive())
        		pm.currentTransaction().rollback();
        }
        log.info("new album was successfully saved. name = " + imageFile.getName() +
        		", description = " + imageFile.getDescription());
	}
	
	/* (non-Javadoc)
	 * @see ois.model.impl.ModelManager#getAlbums()
	 */
	public Iterable<AlbumFile> getAlbums(){
		open();
		Iterable<AlbumFile> albums = pm.getExtent(AlbumFile.class);
		return albums;
	}
	
	/* (non-Javadoc)
	 * @see ois.model.impl.ModelManager#saveAlbum(ois.model.AlbumFile)
	 */
	public void saveAlbum(AlbumFile album) throws PersistanceManagerException{
		open();
		pm.currentTransaction().begin();
		try {
			// if album file does not have a key, create one
			if (album.getKey() == null)
				album.setKey( new KeyFactory.Builder(AlbumFile.class.getSimpleName(), album.getName()).getKey() );
            
			pm.makePersistent(album);
            pm.currentTransaction().commit();
        }catch(Exception e){
        	PersistanceManagerException pme = new PersistanceManagerException("Cannot save album. Name = " + album.getName() , e);
        	throw pme;
        } finally {
        	if(pm.currentTransaction().isActive())
        		pm.currentTransaction().rollback();
        }
        log.info("new album was successfully saved. name = " + album.getName() +
        		", location = " + album.getDescription());
	}

	/* (non-Javadoc)
	 * @see ois.model.ModelManager#getAlbum(long)
	 */
	public AlbumFile getAlbumFile(Key key) throws PersistanceManagerException {
		open();
		return pm.getObjectById(AlbumFile.class, key);
	}

	/* (non-Javadoc)
	 * @see ois.model.ModelManager#getAlbum(long)
	 */
	public ImageFile getImageFile(Key key) throws PersistanceManagerException {
		open();
		return pm.getObjectById(ImageFile.class, key);
	}

	/* (non-Javadoc)
	 * @see ois.model.ModelManager#deleteAlbum(ois.model.AlbumFile)
	 */
	public void deleteAlbum(AlbumFile album) throws PersistanceManagerException {
        open();
        pm.currentTransaction().begin();
        try {
        	pm.deletePersistent(album);
        	pm.currentTransaction().commit();
        }catch(Exception e){
        	PersistanceManagerException pme = new PersistanceManagerException("Album [" + album.getName() + "] could not be deleted",e);
        	throw pme;
        } finally {
        	if(pm.currentTransaction().isActive())
        		pm.currentTransaction().rollback();
        }
	}

	
	/* (non-Javadoc)
	 * @see ois.model.ModelManager#getImageLink(long, java.lang.String)
	 */
	public String getImageLink(String keyString, String extension){
		return "/images/" + keyString + "." + extension;
	}

	/* (non-Javadoc)
	 * @see ois.model.ModelManager#getImages()
	 */
	@Override
	public Iterable<ImageFile> getAllImages() {
		open();
		Iterable<ImageFile> images = pm.getExtent(ImageFile.class);
		return images;
	}

	@Override
	public List<ImageFile> getImageFilesByAlbum(Key albumKey) {
		open();
		AlbumFile album = pm.getObjectById(AlbumFile.class, albumKey);
		return album.getImages();
	}

	@Override
	public void close() {
		if (pm != null){
			pm.close();
			pm = null;
		}
	}
	
	/**
	 * Instantiates persistent manager.
	 */
	private void open(){
		//TODO check pm.isOpen instead
		if (pm == null)
			pm = PMF.get().getPersistenceManager();
	}

	@Override
	public void addImageToAlbum(ImageFile imageFile, Key albumFileKey) throws PersistanceManagerException {
        open();
        pm.currentTransaction().begin();
        try {
        	AlbumFile album = pm.getObjectById(AlbumFile.class,albumFileKey);
        	imageFile.setAlbumKey(album.getKey());
        	album.getImages().add(imageFile);
        	pm.currentTransaction().commit();
        }catch(Exception e){
        	PersistanceManagerException pme = new PersistanceManagerException("Error while saving Image("+imageFile.getName()+")",e);
        	throw pme;
        } finally {
        	if(pm.currentTransaction().isActive())
        		pm.currentTransaction().rollback();
        }
        log.info("Image File [" + imageFile.getKey() + "] was added to its parent album file");
	}

	@Override
	public void addDataToImage(ImageData imageData, Key imageFileKey) throws PersistanceManagerException {
		open();
        pm.currentTransaction().begin();
        try {
        	ImageFile imageFile = pm.getObjectById(ImageFile.class,imageFileKey);
        	imageData.setImageFileKey(imageFile.getKey());
        	imageFile.getImageData().add(imageData);
        	//transaction is closed some how find out why
        	pm.currentTransaction().commit();
        }catch(Exception e){
        	PersistanceManagerException pme = new PersistanceManagerException("Error while saving ImageData",e);
        	throw pme;
        } finally {
        	if(pm.currentTransaction().isActive())
        		pm.currentTransaction().rollback();
        }
        log.info("Image Data [" + imageData.getKey() + "] was added to its parent image file");
	}

	
}
