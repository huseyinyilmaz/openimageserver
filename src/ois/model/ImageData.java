package ois.model;

import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Blob;
import com.google.appengine.api.datastore.Key;


/**
 * @author Huseyin Yilmaz
 * Holds Text data in datastore.
 * This class designed to hold types like jpeg,png or gif.
 * File information will be hold in handle of this data.
 */
@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class ImageData{
	
    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;
	@Persistent
    private Blob data;
	/*
	@Persistent
	private ImageFileType type;
	*/
	@Persistent
    private ImageFile imageFile;
	@Persistent
	private Date creationDate;

	
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
	 * Getter for this object's type
	 * @return type of this object
	 */
/*
	public ImageFileType getType() {
		return type;
	}
*/	
	/**
	 * Setter for this object's type
	 * @param type type that will be assigned to this object
	 */
/*
	public void setType(ImageFileType type) {
		this.type = type;
	}
*/
	/**
	 * Getter for key of this object.
	 * @return Datastore Key of this object
	 */
	public Key getKey() {
		return key;
	}

	/**
	 * Getter for this objects data.
	 * @return data of this object
	 */
	public Blob getData() {
		return data;
	}
	
	/**
	 * Setter for this object's data
	 * @param data data that will be assigned to this object
	 */
	public void setData(Blob data) {
		this.data = data;
	}
	
	/**
	 * Getter for this object's handler
	 * @return imageFile of this object
	 */
	public ImageFile getImageFile() {
		return imageFile;
	}

	
}