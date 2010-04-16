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
import ois.model.PMF;

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
	public AlbumFile getAlbumFile(long id) throws PersistanceManagerException{
		open();
		return pm.getObjectById(AlbumFile.class, id);
	}

	/* (non-Javadoc)
	 * @see ois.model.ModelManager#getAlbum(long)
	 */
	public ImageFile getImageFile(long id) throws PersistanceManagerException{
		open();
		return pm.getObjectById(ImageFile.class, id);
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
	public String getImageLink(long id,String extension) {
		return "/images/" + id + "." + extension;
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

	@SuppressWarnings("unchecked")
	@Override
	public List<ImageFile> getImages(long albumId) {
		open();
		Query query = pm.newQuery(ImageFile.class);
		query.setFilter("albumId == id");
		query.declareParameters("long id");
		List<ImageFile> list;
		try{
			list = (List<ImageFile>) query.execute(albumId);
		} finally {
	        query.closeAll();
	    }	
		return list;
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
		if (pm == null)
			pm = PMF.get().getPersistenceManager();
	}

	@Override
	public void addImageToAlbum(ImageFile imageFile, long albumId) throws PersistanceManagerException {
        open();
        pm.currentTransaction().begin();
        try {
        	AlbumFile album = pm.getObjectById(AlbumFile.class,albumId);
        	album.getImages().add(imageFile);
        	pm.currentTransaction().commit();
        }catch(Exception e){
        	PersistanceManagerException pme = new PersistanceManagerException("Error while saving Image("+imageFile.getName()+")",e);
        	throw pme;
        } finally {
        	if(pm.currentTransaction().isActive())
        		pm.currentTransaction().rollback();
        }
	}

	@Override
	public void addDataToImage(ImageData imagedata, long imageId) throws PersistanceManagerException {
        open();
        pm.currentTransaction().begin();
        try {
        	ImageFile imageFile = pm.getObjectById(ImageFile.class,imageId);
        	imageFile.getImageData().add(imagedata);
        	pm.currentTransaction().commit();
        }catch(Exception e){
        	PersistanceManagerException pme = new PersistanceManagerException("Error while saving ImageData",e);
        	throw pme;
        } finally {
        	if(pm.currentTransaction().isActive())
        		pm.currentTransaction().rollback();
        }
	}



	
}
