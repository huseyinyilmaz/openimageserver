package ois.view;

import java.util.Date;

import ois.ApplicationManager;

public class DataBean {
	private String 	keyString;
	private String 	typeString;
	private Date 	creationDate;
	private int		width;
	private int		height;
	private boolean enhanced;
	private boolean original;
	private boolean thumbnail;
	
	
	public String getLink(){
		return ApplicationManager.getImageLink(keyString);
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
