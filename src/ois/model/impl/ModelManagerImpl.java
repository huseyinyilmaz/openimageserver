package ois.model.impl;

import java.util.List;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import ois.exceptions.PersistanceManagerException;
import ois.model.AlbumFile;
import ois.model.ImageFile;
import ois.model.ImageFileType;
import ois.model.ModelManager;
import ois.model.PMF;

public class ModelManagerImpl implements ModelManager {
	private static final Logger log = Logger.getLogger(ModelManagerImpl.class.getName());
	private PersistenceManager pm;
	/* (non-Javadoc)
	 * @see ois.model.impl.ModelManager#saveImage(ois.model.ImageFile)
	 */
	public void saveImageFile(ImageFile imageFile) throws PersistanceManagerException{
		if (pm == null)
			pm = PMF.get().getPersistenceManager();
		try {
            pm.makePersistent(imageFile);
        }catch(Exception e){
        	PersistanceManagerException pme = new PersistanceManagerException("Cannot save album. Name = " + imageFile.getName() , e);
        	throw pme;
        } finally {
            pm.close();
            pm = null;
        }
        log.info("new album was successfully saved. name = " + imageFile.getName() +
        		", description = " + imageFile.getDescription());
	}
	
	/* (non-Javadoc)
	 * @see ois.model.impl.ModelManager#getAlbums()
	 */
	public Iterable<AlbumFile> getAlbums(){
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Iterable<AlbumFile> albums = pm.getExtent(AlbumFile.class);
		return albums;
	}
	
	/* (non-Javadoc)
	 * @see ois.model.impl.ModelManager#getImage(java.lang.String, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public ImageFile getImage(String location,String name){
		ImageFile image;
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		Query query = pm.newQuery(ImageFile.class);
		query.setFilter("name == fileName");
		query.setFilter("location == fileLocation");
		query.declareParameters("java.lang.String fileLocation , java.lang.String fileName");
		//query.declareParameters("java.lang.String fileName");
		List<ImageFile> list;
		try{
		list = (List<ImageFile>) query.execute(location, name);
		if (list.size() == 0)
			image = null;
		else if(list.size() == 1)
			image = list.get(0); 
		else
			throw new IllegalArgumentException("More than one image matched the arguments");
		} finally {
	        query.closeAll();
	    }	
		
		
		return image;
	}
	
	/* (non-Javadoc)
	 * @see ois.model.impl.ModelManager#getImageType(java.lang.String)
	 */
	public ImageFileType getImageType(String typeString){
		for (ImageFileType ft : ImageFileType.values()){
			if(typeString.equals(ft.toString()))
				return ft;
		}
		log.warning("Cannot contruct a Binary File type with string \"" + typeString + "\"");
		throw new IllegalArgumentException("Cannot contruct a Binary File type with string \"" + typeString + "\"");
	}
	
	/* (non-Javadoc)
	 * @see ois.model.impl.ModelManager#saveAlbum(ois.model.AlbumFile)
	 */
	public void saveAlbum(AlbumFile album) throws PersistanceManagerException{
		if (pm == null)
			pm = PMF.get().getPersistenceManager();
		try {
            pm.makePersistent(album);
        }catch(Exception e){
        	PersistanceManagerException pme = new PersistanceManagerException("Cannot save album. Name = " + album.getName() , e);
        	throw pme;
        } finally {
            pm.close();
            pm = null;
        }
        log.info("new album was successfully saved. name = " + album.getName() +
        		", location = " + album.getDescription());
	}

	/* (non-Javadoc)
	 * @see ois.model.ModelManager#getAlbum(long)
	 */
	public AlbumFile getAlbumFile(long id) throws PersistanceManagerException{
		//persistent manager is begin hold in this object. so if we do changes on object,
		//we will use same pm object
		pm = PMF.get().getPersistenceManager();
		return pm.getObjectById(AlbumFile.class, id);
	}

	/* (non-Javadoc)
	 * @see ois.model.ModelManager#getAlbum(long)
	 */
	public ImageFile getImageFile(long id) throws PersistanceManagerException{
		//persistent manager is begin hold in this object. so if we do changes on object,
		//we will use same pm object
		pm = PMF.get().getPersistenceManager();
		return pm.getObjectById(ImageFile.class, id);
	}

	/* (non-Javadoc)
	 * @see ois.model.ModelManager#deleteAlbum(ois.model.AlbumFile)
	 */
	public void deleteAlbum(AlbumFile album) throws PersistanceManagerException {
        try {
        	pm.deletePersistent(album);
        }catch(Exception e){
        	PersistanceManagerException pme = new PersistanceManagerException("ToDoList [" + album.getName() + "] could not be Deleted",e);
        	throw pme;
        } finally {
            pm.close();
            pm = null;
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
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Iterable<ImageFile> images = pm.getExtent(ImageFile.class);
		return images;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ImageFile> getImages(long albumId) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		Query query = pm.newQuery(ImageFile.class);
		query.setFilter("albumId == id");
		query.declareParameters("long id");
		//query.declareParameters("java.lang.String fileName");
		List<ImageFile> list;
		try{
		list = (List<ImageFile>) query.execute(albumId);
		} finally {
	        query.closeAll();
	    }	
		return list;

	}

	@Override
	public void closePM() {
		pm.close();
	}

	@Override
	public void openPM() {
		pm = PMF.get().getPersistenceManager();
	}

	
}
