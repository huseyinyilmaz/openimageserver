package ois.model;

import java.util.List;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import ois.exceptions.PersistanceManagerException;

public class ModelManager {
	private static final Logger log = Logger.getLogger(ModelManager.class.getName());

	public static void saveImage(ImageFile file) throws PersistanceManagerException{
		PersistenceManager pm = PMF.get().getPersistenceManager();

		try {
			pm.makePersistent(file.getImageData().get(0));
            pm.makePersistent(file);
        }catch(Exception e){
        	PersistanceManagerException pme = new PersistanceManagerException("Cannot save image. Name = " + file.getName() +
        																		", location = " + file.getLocation(),e);
        	throw pme;
        } finally {
            pm.close();
        }
        log.info("new image was successfully saved. name = " + file.getName() +
        		", location = " + file.getLocation());
	}
	
	public static Iterable<AlbumFile> getAlbums(){
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Iterable<AlbumFile> albums = pm.getExtent(AlbumFile.class);
		return albums;
	}
	
	@SuppressWarnings("unchecked")
	public static ImageFile getImage(String location,String name){
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
	
	public static ImageFileType getImageType(String typeString){
		for (ImageFileType ft : ImageFileType.values()){
			if(typeString.equals(ft.toString()))
				return ft;
		}
		log.warning("Cannot contruct a Binary File type with string \"" + typeString + "\"");
		throw new IllegalArgumentException("Cannot contruct a Binary File type with string \"" + typeString + "\"");
	}
	
	public static void saveAlbum(AlbumFile album) throws PersistanceManagerException{
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
            pm.makePersistent(album);
        }catch(Exception e){
        	PersistanceManagerException pme = new PersistanceManagerException("Cannot save album. Name = " + album.getName() , e);
        	throw pme;
        } finally {
            pm.close();
        }
        log.info("new album was successfully saved. name = " + album.getName() +
        		", location = " + album.getDescription());
	
	}
}
