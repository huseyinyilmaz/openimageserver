package ois.controller.impl;

import java.util.ArrayList;
import java.util.Collections;
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
import ois.view.ImageLink;

import com.google.appengine.api.datastore.Blob;

public class ControllerManagerImpl implements ControllerManager{
	private static final Logger log = Logger.getLogger(ControllerManagerImpl.class.getName());
	private ModelManager modelManager;
	
	/* (non-Javadoc)
	 * @see ois.controller.ControllerManager#saveImage(ois.controller.Image)
	 */
	public void createImage(Image img) throws PersistanceManagerException{
		//if(img.getAlbum().trim().equals(""))
		//	throw new IllegalArgumentException("Location cannot be null or consist of only white spaces");
		AlbumFile album = modelManager.getAlbum(img.getAlbum());
		ImageFile file = new ImageFile();
		file.setName(img.getName());
		//create album-image connection
		file.setAlbum(album);
		album.getImages().add(file);
		
		log.info("new image was successfully redirected to ModelManagerImpl. name = " + file.getName() +
        		", album = " + file.getAlbum().getName());
		//modelManager.createImage(file);
		//modelManager.saveAlbum(album);
		long id = file.getKey().getId();
		//file = modelManager.getImageFile(id);
		ImageData data = new ImageData(file,new Blob(img.getData()),ois.model.ImageFileType.fromString(img.getType()));
		file.getImageData().add(data);
		modelManager.saveAlbum(album);

		//modelManager.saveImageFile(file);
	}
	
	/* (non-Javadoc)
	 * @see ois.controller.ControllerManager#getImage(java.lang.String, java.lang.String)
	 */
	public Image getImage(String location , String name){
		ImageFile bf = modelManager.getImage(location, name);
		Image file = null;
		if(bf != null)
			//file = new ImageLink(bf.getName(),bf.getLocation(),bf.getImageData().get(0).getData().getBytes(),bf.getImageData().get(0).getType().toString());
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

	
	/* (non-Javadoc)
	 * @see ois.controller.ControllerManager#saveAlbum(ois.controller.Album)
	 */
	public void saveAlbum(Album album) throws PersistanceManagerException {
		AlbumFile albumFile = modelManager.getAlbum(album.getId());
		albumFile.setName(album.getName());
		albumFile.setDescription(album.getDescription());
		modelManager.saveAlbum(albumFile);
		
	}

	/* (non-Javadoc)
	 * @see ois.controller.ControllerManager#getImageLinks(long)
	 */
	public List<ImageLink> getImageLinks(long id) throws PersistanceManagerException {
		//if Id is 0 this means we don't return any image link
		//we just return an empty list.
		if(id == 0)
			return Collections.emptyList();
		//list that will hold result image links
		List<ImageLink> images = new ArrayList<ImageLink>();

		Iterable<ImageFile> imageFiles;
		if(id==-1)
			//get all the images in db
			imageFiles = modelManager.getImages();
		else
			//get images only given album contains
			imageFiles = modelManager.getAlbum(id).getImages();
		
		for( ImageFile imageFile : imageFiles ){
			//create an image and set properties
			ImageLink image = new ImageLink();
			image.setCreationDate(imageFile.getCreationDate());
			image.setDescription(imageFile.getDescription());
			image.setId(imageFile.getKey().getId());
			image.setName(image.getName());
			image.setLink(modelManager.getImageLink(image.getId(),imageFile.getType().getExtension()));
			//add image to image list.
			images.add(image);
		}
		return images;
	}

}
