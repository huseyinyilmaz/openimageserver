package ois.controller;

public class Album {
	private String name;
	private long key;
	
	
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
	public Album(String name, long key) {
		super();
		this.name = name;
		this.key = key;
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
