package ois.view;

public enum CSActionType {
	CREATE_ALBUM("createAlbum"),
	EDIT_ALBUM("editAlbum"),
	DELETE_ALBUM("deleteAlbum");
	
	private String value;
	
	CSActionType(String value){
		this.value = value;
	}

	/* (non-Javadoc)
	 * @see java.lang.Enum#toString()
	 */
	@Override
	public String toString() {
		return this.value;
	};
	
	/**
	 * returns enum representation of given string. If no match was found returns
	 * null value.
	 * @param value String value of CSParamType
	 * @return CSActionType representation of given script
	 */
	public static CSActionType fromString(String value) {
		CSActionType param = null;
		for (CSActionType p : values())
			if( p.toString().equals(value)){
				param = p;
				break;
			}
		return param;
	}
	
	
}
