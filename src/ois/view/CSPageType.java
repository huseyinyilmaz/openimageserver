package ois.view;

public enum CSPageType {
	MAIN("main"),
	IMAGE("image"),
	ALBUM_EDIT("albumEdit"),
	IMAGE_CREATE("imageCreate");
	
	private String value;
	
	CSPageType(String value){
		this.value = value;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Enum#toString()
	 */
	@Override
	public String toString() {
		return this.value;
	}

	/**
	 * returns enum representation of given string. If no match was found returns
	 * null value.
	 * @param value String value of CSParamType
	 * @return CSPageType representation of given script
	 */
	public static CSPageType fromString(String value) {
		CSPageType param = null;
		for (CSPageType p : values())
			if( p.toString().equals(value)){
				param = p;
				break;
			}
		return param;
	}
	
	

}
