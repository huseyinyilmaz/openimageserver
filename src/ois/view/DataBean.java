package ois.view;

import java.util.Date;

import ois.ApplicationManager;

public class DataBean {
	private String 	keyString;
	private String 	imageKeyString;
	private String 	typeString;
	private Date 	creationDate;
	private int		width;
	private int		height;
	private boolean enhanced;
	private boolean original;
	private boolean thumbnail;
	
	public DataBean (){
		super();
	}
	public DataBean(String keyString,String imageKeyString){
		this.keyString = keyString;
		this.imageKeyString = imageKeyString;
	}
	
	public String getLink(){
		return ApplicationManager.IMAGE_URI_PREFIX + keyString;
	}

	public String getFullLink(){
		return ApplicationManager.getServerURL() + getLink();
	}
	public String getViewLink(){
		return ApplicationManager.MAIN_PAGE + "?"
		+ CSParamType.PAGE.toString() + "=" + CSPageType.IMAGE_REVISIONS.toString()
		+ "&" + CSParamType.IMAGE.toString() + "=" + imageKeyString
		+ "&" + CSParamType.REVISION.toString() + "=" + keyString;
	}
	
	public String getName(){
		String name;
		if(keyString.equals(ApplicationManager.NONE))
			name = "None";
		else
			name = width + "x" + height;
		return name;
	}
	
	public String getCreationDateString(){
		return creationDate.toString();
	}

	public String getImageKeyString() {
		return imageKeyString;
	}

	public void setImageKeyString(String imageBeanKeyString) {
		this.imageKeyString = imageBeanKeyString;
	}

	/**
	 * @return the keyString
	 */
	public String getKeyString() {
		return keyString;
	}
	/**
	 * @param keyString the keyString to set
	 */
	public void setKeyString(String keyString) {
		this.keyString = keyString;
	}
	/**
	 * @return the typeString
	 */
	public String getTypeString() {
		return typeString;
	}
	/**
	 * @param typeString the typeString to set
	 */
	public void setTypeString(String typeString) {
		this.typeString = typeString;
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
	 * @return the width
	 */
	public int getWidth() {
		return width;
	}
	/**
	 * @param width the width to set
	 */
	public void setWidth(int width) {
		this.width = width;
	}
	/**
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}
	/**
	 * @param height the height to set
	 */
	public void setHeight(int height) {
		this.height = height;
	}
	/**
	 * @return the enhanced
	 */
	public boolean isEnhanced() {
		return enhanced;
	}
	/**
	 * @param enhanced the enhanced to set
	 */
	public void setEnhanced(boolean enhanced) {
		this.enhanced = enhanced;
	}
	/**
	 * @return the original
	 */
	public boolean isOriginal() {
		return original;
	}
	/**
	 * @param original the original to set
	 */
	public void setOriginal(boolean original) {
		this.original = original;
	}
	/**
	 * @return the thumbnail
	 */
	public boolean isThumbnail() {
		return thumbnail;
	}
	/**
	 * @param thumbnail the thumbnail to set
	 */
	public void setThumbnail(boolean thumbnail) {
		this.thumbnail = thumbnail;
	}
	
	
}
