package ois.controller;

import java.util.Date;

public class Album {
	private String name;
	private String description;
	private String keyString;
	private Date creationDate;
	
	/**
	 * Generates an empty album 
	 */
	public Album() {
		super();
	}
	/**
	 * generates a new album from given name and keyString
	 * @param name
	 * @param id
	 */
	public Album(String key,String name) {
		super();
		this.name = name;
		this.keyString = key;
	}

	/**
	 * generates a new album from given name ,keyString,description and creationdate
	 * @param id
	 * @param name
	 * @param description
	 * @param creationDate
	 */
	public Album(String key,String name,String description,Date creationDate) {
		super();
		this.name = name;
		this.keyString = key;
		this.description = description;
		this.creationDate = creationDate;
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
	 * @return the keyString
	 */
	public String getKeyString() {
		return keyString;
	}
	/**
	 * @param id the keyString to set
	 */
	public void setKeyString(String key) {
		this.keyString = key;
	}
	
}
