package ois.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Image {
	private String albumKeyString;
	private String keyString; 

	private String name;
    private List<Data> dataList = new ArrayList<Data>();
    private String type;
	private String description;
    private Date creationDate;

	/**
     * Creates an empty image
     */
    public Image(){
    	super();
    }
    
    /**
     * Generate new image file with given parameters
	 * @param name
	 * @param album
	 * @param data
	 */
	public Image(String name, String album, byte[] data,String type) {
		super();
		this.name = name;
		this.albumKeyString = album;
		Data imageData = new Data();
		imageData.setData(data);
		imageData.setOriginal(true);
		this.dataList.add(imageData);
		this.type = type;
	}
	//Getters and setters
    public String getKeyString() {
		return keyString;
	}
	public void setKeyString(String keyString) {
		this.keyString = keyString;
	}
	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	/**
	 * @return the album
	 */
	public String getAlbum() {
		return albumKeyString;
	}
	/**
	 * @param album the album to set
	 */
	public void setAlbum(String album) {
		this.albumKeyString = album;
	}
	/**
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
	 * @return the data
	 */
	public List<Data> getDataList() {
		return dataList;
	}
    /**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
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

}
