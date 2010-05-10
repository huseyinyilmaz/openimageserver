package ois.model;


public enum ImageType {
	JPEG("image/jpeg","jpg"),
	PNG("image/png","png"),
	GIF("image/gif","gif");
	
	private final String typeString;
	private final String fileExtension;

	ImageType(String typeString,String fileExtension) {
    	this.typeString = typeString;
    	this.fileExtension = fileExtension;
    }
	/* (non-Javadoc)
	 * @see java.lang.Enum#toString()
	 */
	@Override
	public String toString() {
		return this.typeString;
	}
	
	/**
	 * returns extension for this file type
	 * @return file extension for this file type
	 */
	public String getExtension(){
		return this.fileExtension;
	}

	/**
	 * returns enum representation of given string if no match was found returns
	 * null value.
	 * @param value String value of CSParamType
	 * @return CSParamType representation of given script
	 */
	public static ImageType fromString(String value) {
		ImageType param = null;
		for (ImageType p : values())
			if( p.toString().equals(value)){
				param = p;
				break;
			}
		return param;
	}


}
