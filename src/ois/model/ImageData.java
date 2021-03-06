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
	
	@Persistent
	private ImageType type;
	
    @Persistent
    private Key imageFileKey;
	
	@Persistent
	private Date creationDate;

    @Persistent
    private int width;
    @Persistent
    private int height;
    @Persistent
    private boolean enhanced;
    @Persistent
    private boolean original;
    @Persistent
    private boolean thumbnail;

	public ImageData(){}//to make image data serializable
    public ImageData (Blob data,ImageType type){
    	//this.imageFile = imageFile;
    	this.data = data;
    	this.type = type;
    }
	
	//-------------Getters and setters--------------------------
	/**
	 * @return the thumbnail
	 */
	public boolean isThumbnail() {
		return thumbnail;
	}
	/**
	 * @param thumbnail the thumbnail to set
	 */
	public void setThumbnail(boolean thumbnail) {
		this.thumbnail = thumbnail;
	}
    /**
	 * @return the isOriginal
	 */
	public boolean isOriginal() {
		return original;
	}
	/**
	 * @param isOriginal the isOriginal to set
	 */
	public void setOriginal(boolean original) {
		this.original = original;
	}
    
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

	public ImageType getType() {
		return type;
	}
	
	/**
	 * Setter for this object's type
	 * @param type type that will be assigned to this object
	 */

	public void setType(ImageType type) {
		this.type = type;
	}

	/**
	 * Getter for key of this object.
	 * @return Datastore Key of this object
	 */
	public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
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
	 * Getter for this object's image width
	 * @return the width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * @param width the width to set
	 */
	public void setWidth(int width) {
		this.width = width;
	}

	/**
	 * Getter for this object's image height
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * @param height the height to set
	 */
	public void setHeight(int height) {
		this.height = height;
	}

	/**
	 * @return the enhanced
	 */
	public boolean isEnhanced() {
		return enhanced;
	}

	/**
	 * Setter for this object's enhanced status.
	 * (Enhanced or not enhanced)
	 * @param enhanced the enhanced to set
	 */
	public void setEnhanced(boolean enhanced) {
		this.enhanced = enhanced;
	}

	public Key getImageFileKey() {
		return imageFileKey;
	}
	public void setImageFileKey(Key imageFileKey) {
		this.imageFileKey = imageFileKey;
	}

	
}
