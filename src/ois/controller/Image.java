package ois.controller;

public class Image {
	private String album;
    private String name;
    private byte[] data;
    private String type;
	private String description;
    

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
		this.album = album;
		this.data = data;
		this.type = type;
	}
	//Getters and setters
    /**
	 * @return the album
	 */
	public String getAlbum() {
		return album;
	}
	/**
	 * @param album the album to set
	 */
	public void setAlbum(String album) {
		this.album = album;
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
