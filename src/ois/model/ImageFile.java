package ois.model;

//import java.util.ArrayList;
import java.util.ArrayList;
import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Blob;
import com.google.appengine.api.datastore.Key;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class ImageFile {
    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;
    @Persistent
    private String name;
    @Persistent(mappedBy = "imageFile")
    private final ArrayList<ImageData> imageData = new ArrayList<ImageData>();
	@Persistent
	private AlbumFile album;
	@Persistent
	private Date creationDate;
    @Persistent
    private String description;
	@Persistent
	private ImageFileType type;
	


	public ImageFile(){}//to make ImageFile serializable
	//Creates an empty Image File
	public ImageFile(Blob data , ImageFileType type){
		this.type = type;
		ImageData d = new ImageData(this,data,type);
		this.imageData.add(d);
	}

	//-------------Getters and setters--------------------------
	/**
	 * @return the creationDate
	 */
	public Date getCreationDate() {
		return creationDate;
	}

	/**
	 * @param creationDate the creationDate to set
	 */
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	/**
	 * Getter for the album this object belongs to
	 * @return the album
	 */
	public AlbumFile getAlbum() {
		return album;
	}
	/**
	 * @param album album that this object belongs to
	 */
	public void setAlbum(AlbumFile album) {
		this.album = album;
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
	
}
