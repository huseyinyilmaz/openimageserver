package ois.model;


public enum ImageType {
	//	HTTP		file 		img src		img manipulator
	//				extension	type		return type
	//-----------------------------------------------
	JPEG("image/jpeg",	"jpg",	"JPEG"		,"JPEG"),
	JPG2("image/pjpeg",	"jpg",	"JPEG"		,"JPEG"),//this is jpeg for IE :(
	PNG("image/png",	"png",	"PNG"		,"PNG"),
	BMP("image/bmp",	"bmp",	"BMP"		,"PNG"),
	TIFF("image/tiff",	"tiff",	"TIFF"		,"PNG"),
	ICO("image/vnd.microsoft.icon","ico","ICO","PNG"),
	ICO2("image/x-icon","ico","ICO","PNG");
	private final String contentType;
	private final String fileExtension;
	//image type that image manipulation service is using
	private final String imageType;
	private final String imageManipulatorReturnType;
	
	ImageType(String typeString,String fileExtension,String imageType,String imageManipulatorReturnType) {
    	this.contentType = typeString;
    	this.fileExtension = fileExtension;
    	this.imageType = imageType;
    	this.imageManipulatorReturnType = imageManipulatorReturnType;
    }
	/* (non-Javadoc)
	 * @see java.lang.Enum#toString()
	 */
	@Override
	public String toString() {
		return this.getContentType();
	}
	
	public String getContentType(){
		return this.contentType;
	}
	
	public String getImageType(){
		return this.imageType;
	}
	
	public String getImageManipulatorReturnType(){
		return this.imageManipulatorReturnType;
	}
	/**
	 * returns extension for this file type
	 * @return file extension for this file type
	 */
	public String getFileExtension(){
		return this.fileExtension;
	}

	/**
	 * returns enum representation of given string if no match was found returns
	 * null value.
	 * @param value String value of CSParamType
	 * @return CSParamType representation of given script
	 */
	public static ImageType fromContentType(String value) {
		ImageType param = null;
		for (ImageType p : values())
			if( p.getContentType().equals(value)){
				param = p;
				break;
			}
		//convert microsoft's jpeg type to standard one.
		if(param == JPG2)
			param = JPEG;
		return param;
	}
	
	public static ImageType fromImageType(String value){
		ImageType param = null;
		for (ImageType p : values())
			if( p.getImageType().equals(value)){
				param = p;
				break;
			}
		return param;
	}


}
