package ois.model;

//import java.util.ArrayList;
import java.util.ArrayList;
import java.util.Date;

import javax.jdo.annotations.Element;
import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class ImageFile {
    @PrimaryKey
    @Persistent
    private Key key;
    @Persistent
    private String name;
    @Persistent(mappedBy = "imageFile")
    @Element(dependent = "true") 
    private final ArrayList<ImageData> imageData = new ArrayList<ImageData>();
    
    @Persistent
	private AlbumFile album;
    @Persistent
    @Extension(vendorName="datanucleus", key="gae.parent-pk", value="true")
    private Key albumKey;
    
	@Persistent
	private Date creationDate = new Date();
    @Persistent
    private String description;
	@Persistent
	private ImageFileType type;
	

	//Creates an empty Image File
	public ImageFile(){}//to make ImageFile serializable

	//-------------Getters and setters--------------------------

	public Key getAlbumKey() {
		return albumKey;
	}

	public void setAlbumKey(Key albumKey) {
		this.albumKey = albumKey;
	}
	/**
	 * @return the creationDate
	 */
	public Date getCreationDate() {
		return creationDate;
	}

	public void setType(ImageFileType type) {
		this.type = type;
	}

	/**
	 * @param creationDate the creationDate to set
	 */
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	
	/**
	 * Getter for this object's data object
	 * @return the data
	 */
	public ArrayList<ImageData> getImageData() {
		return imageData;
	}

	/**
	 * Getter for this object's key
	 * @return the key
	 */
	public Key getKey() {
		return key;
	}
	
	
	public void setKey(Key key) {
		this.key = key;
	}

	/**
	 * Getter for this object's name
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * @return the type
	 */
	public ImageFileType getType() {
		return type;
	}
	/**
	 * @return the album
	 */
	public AlbumFile getAlbum() {
		return album;
	}
}
