package ois.controller;

import java.util.Date;

public class Album {
	private String name;
	private String description;
	private long key;
	private Date creationDate;
	
	/**
	 * Generates an empty album 
	 */
	public Album() {
		super();
	}
	/**
	 * generates a new album from given name and key
	 * @param name
	 * @param key
	 */
	public Album(long key,String name) {
		super();
		this.name = name;
		this.key = key;
	}

	/**
	 * generates a new album from given name ,key,description and creationdate
	 * @param key
	 * @param name
	 * @param description
	 * @param creationDate
	 */
	public Album(long key,String name,String description,Date creationDate) {
		super();
		this.name = name;
		this.key = key;
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
	 * @return the key
	 */
	public long getKey() {
		return key;
	}
	/**
	 * @param key the key to set
	 */
	public void setKey(long key) {
		this.key = key;
	}
	
}
