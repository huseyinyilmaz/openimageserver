package ois.model;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class AlbumFile {
    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;
    @Persistent
    private String name;
    @Persistent
    private String description;
    @Persistent(mappedBy = "album")
    private List<ImageFile> images = new LinkedList<ImageFile>();
	@Persistent
	private Date creationDate;
	
	public AlbumFile(){
		images = new LinkedList<ImageFile>();
		creationDate = new Date();
	}

	public AlbumFile(String name,String descripton){
		images = new LinkedList<ImageFile>();
		setName(name);
		setDescription(descripton);
		creationDate = new Date();
	}

	
	//-------------Getters and setters--------------------------
	/**
	 * @return the creationDate
	 */
	public Date getCreationDate() {
		return creationDate;
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
	 * Getter for this object's description
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
	 * Getter for this object's image list
	 * @return the image list
	 */
	public List<ImageFile> getImages() {
		return images;
	}


}