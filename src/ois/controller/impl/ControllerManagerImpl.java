package ois.controller.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import ois.controller.Album;
import ois.controller.ControllerManager;
import ois.controller.Image;
import ois.exceptions.PersistanceManagerException;
import ois.model.AlbumFile;
import ois.model.ImageData;
import ois.model.ImageFile;
import ois.model.ModelManager;

import com.google.appengine.api.datastore.Blob;

public class ControllerManagerImpl implements ControllerManager{
	private static final Logger log = Logger.getLogger(ControllerManagerImpl.class.getName());
	private ModelManager modelManager;
	
	/* (non-Javadoc)
	 * @see ois.controller.ControllerManager#saveImage(ois.controller.Image)
	 */
	public void saveImage(Image img) throws PersistanceManagerException{
		if(img.getLocation().trim().equals(""))
			throw new IllegalArgumentException("Location cannot be null or consist of only white spaces");
		ImageFile file = new ImageFile();
		file.setName(img.getName());
		file.setLocation(img.getLocation());

		ImageData data = new ImageData();
		data.setData(new Blob(img.getData()));
		//data.setType(ois.model.ImageFileType.fromString(img.getType());
		file.getImageData().add(data);
		log.info("new image was successfully redirected to ModelManagerImpl. name = " + file.getName() +
        		", location = " + file.getLocation());
		modelManager.saveImage(file);
	}
	
	/* (non-Javadoc)
	 * @see ois.controller.ControllerManager#getImage(java.lang.String, java.lang.String)
	 */
	public Image getImage(String location , String name){
		ImageFile bf = modelManager.getImage(location, name);
		Image file = null;
		if(bf != null)
			//file = new Image(bf.getName(),bf.getLocation(),bf.getImageData().get(0).getData().getBytes(),bf.getImageData().get(0).getType().toString());
			file = null;//TODO remove this
		return file;
	}

	/* (non-Javadoc)
	 * @see ois.controller.ControllerManager#getAlbums()
	 */
	public List<Album> getAlbums(){
		List<Album> albums = new ArrayList<Album>();
		for(AlbumFile album: modelManager.getAlbums()){
			albums.add( new Album(album.getKey().getId(),album.getName(),album.getDescription(),album.getCreationDate()));
		}
		return albums;
	}
	
	/* (non-Javadoc)
	 * @see ois.controller.ControllerManager#createAlbum(java.lang.String, java.lang.String)
	 */
	public void createAlbum(String name, String description) throws PersistanceManagerException{
		
		//TODO check if the name is unique
		if(!Pattern.matches("\\w+",name))
			throw new IllegalArgumentException("name can only be consist of digits , letters or _ characters");
		AlbumFile album = new AlbumFile(name,description);
		modelManager.saveAlbum(album);
	}

	public ControllerManagerImpl(ModelManager modelManager){
		this.modelManager = modelManager;
	}

	/* (non-Javadoc)
	 * @see ois.controller.ControllerManager#deleteAlbum(long)
	 */
	public void deleteAlbum(long id) throws PersistanceManagerException{
		AlbumFile albumFile = modelManager.getAlbum(id);
		if (albumFile == null)
			throw new IllegalArgumentException("Album with id '" + id +" could not be found");
		modelManager.deleteAlbum(albumFile);
	}

	
	/* (non-Javadoc)
	 * @see ois.controller.ControllerManager#getAlbum(long)
	 */
	public Album getAlbum(long id) throws PersistanceManagerException {
		AlbumFile albumFile = modelManager.getAlbum(id);
		return new Album(albumFile.getKey().getId(),albumFile.getName(),albumFile.getDescription(),albumFile.getCreationDate());
		
	}

}
