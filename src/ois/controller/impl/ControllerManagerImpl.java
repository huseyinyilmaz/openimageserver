package ois.controller.impl;

import java.util.List;
import java.util.logging.Logger;
import java.util.ArrayList;

import ois.controller.Album;
import ois.controller.ControllerManager;
import ois.controller.Image;
import ois.exceptions.PersistanceManagerException;
import ois.model.ImageData;
import ois.model.ImageFile;
import ois.model.ModelManager;
import ois.model.AlbumFile;
import com.google.appengine.api.datastore.Blob;

public class ControllerManagerImpl implements ControllerManager {
	private static final Logger log = Logger.getLogger(ControllerManagerImpl.class.getName());

	/* (non-Javadoc)
	 * @see ois.controller.impl.ControllerManager#saveImage(ois.controller.Image)
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
		log.info("new image was successfully redirected to ModelManager. name = " + file.getName() +
        		", location = " + file.getLocation());
		ModelManager.saveImage(file);
	}
	
	/* (non-Javadoc)
	 * @see ois.controller.impl.ControllerManager#getImage(java.lang.String, java.lang.String)
	 */
	public Image getImage(String location , String name){
		ImageFile bf = ModelManager.getImage(location, name);
		Image file = null;
		if(bf != null)
			//file = new Image(bf.getName(),bf.getLocation(),bf.getImageData().get(0).getData().getBytes(),bf.getImageData().get(0).getType().toString());
			file = null;//TODO remove this
		return file;
	}

	/* (non-Javadoc)
	 * @see ois.controller.impl.ControllerManager#getAlbums()
	 */
	public List<Album> getAlbums(){
		List<Album> albums = new ArrayList<Album>();
		for(AlbumFile album: ModelManager.getAlbums()){
			albums.add( new Album(album.getName(),album.getKey().getId()) );
		}
		return albums;
	}
	
	/* (non-Javadoc)
	 * @see ois.controller.impl.ControllerManager#createAlbum(java.lang.String, java.lang.String)
	 */
	public void createAlbum(String name, String description) throws PersistanceManagerException{
		AlbumFile album = new AlbumFile(name,description);
		ModelManager.saveAlbum(album);
	}

}
