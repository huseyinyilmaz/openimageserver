package ois.view;

public enum CSParamType {
	PAGE("page"),
	//ITEM("item"),
	ALBUM("album"),
	IMAGE("image"),
	REVISION("revision"),
	ACTION("action"),
	NAME("name"),
	DESCRIPTION("description"),
	FILE("file"),//uploaded image parameter
	WIDTH("width"),
	HEIGHT("height"),
	ENHANCED("enhanced");
	
	
	private String value;
	
	CSParamType(String value){
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
	 * returns enum representation of given string if no match was found returns
	 * null value.
	 * @param value String value of CSParamType
	 * @return CSParamType representation of given script
	 */
	public static CSParamType fromString(String value) {
		CSParamType param = null;
		for (CSParamType p : values())
			if( p.toString().equals(value)){
				param = p;
				break;
			}
		return param;
	}
}
