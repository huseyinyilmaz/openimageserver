package ois.controller;

public class Image {
	private String location;
    private String name;
    private byte[] data;
    private String type;
	/**
     * Generate new image file with given
	 * @param name
	 * @param location
	 * @param data
	 */
	public Image(String name, String location, byte[] data,String type) {
		super();
		this.name = name;
		this.location = location;
		this.data = data;
		this.type = type;
	}
	//Getters and setters
    /**
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}
	/**
	 * @param location the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
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
	public byte[] getData() {
		return data;
	}
	/**
	 * @param data the data to set
	 */
	public void setData(byte[] data) {
		this.data = data;
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


}
