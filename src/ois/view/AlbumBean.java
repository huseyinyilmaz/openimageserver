package ois.view;

import java.util.List;

import ois.ApplicationManager;

public class AlbumBean {
	private String keyString;
	private String currentImageKeyString;
	private ImageBean currentImageBean = null;
	private String name;
	private String description;
	private List<ImageBean> imageBeanList;

	public AlbumBean(){
		super();
	}

	public AlbumBean(String keyString){
		super();
		this.keyString = keyString;
	}

	public AlbumBean(String keyString,String name){
		super();
		this.name = name;
		this.keyString = keyString;
	}
	
	
	public String getEditLink(){
		return ApplicationManager.MAIN_PAGE + "?" 
			 		+ CSParamType.PAGE.toString() + "=" + CSPageType.ALBUM_EDIT.toString()
			 + "&" + CSParamType.ALBUM.toString() + "=" + keyString;
	}
	
	public String getViewLink(){
		return ApplicationManager.MAIN_PAGE + "?"
		 	 		+ CSParamType.PAGE.toString() + "=" + CSPageType.MAIN.toString()
		 	 + "&" + CSParamType.ALBUM.toString() + "=" + keyString;
	}
	
	public String getCreateImageLink(){
		return ApplicationManager.MAIN_PAGE + "?" 
				+ CSParamType.PAGE.toString() + "=" + CSPageType.IMAGE_CREATE.toString()
	 	  + "&" + CSParamType.ALBUM.toString() + "=" + keyString;
	}
	
	
	public ImageBean getCurrentImageBean(){
		if(currentImageBean == null && imageBeanList!=null){
			for(ImageBean imageBean:imageBeanList)
				if(imageBean.getKeyString().equals(currentImageKeyString))
					currentImageBean = imageBean;
		}
		return currentImageBean;
	}
	
	/**
	 * @return the currentImageKeyString
	 */
	public String getCurrentImageKeyString() {
		return currentImageKeyString;
	}
	/**
	 * @param currentImageKeyString the currentImageKeyString to set
	 */
	public void setCurrentImageKeyString(String currentImageKeyString) {
		this.currentImageKeyString = currentImageKeyString;
		currentImageBean = null;
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
	 * @return the name
	 * @throws Exception 
	 */
	public String getName(){
		if(name.length()>=16)
			return name.substring(0, 14) + "..";
		else
			return name;
	}

	public String getOriginalName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
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
	/**
	 * @return the imageBeanList
	 */
	public List<ImageBean> getImageBeanList() {
		return imageBeanList;
	}
	/**
	 * @param imageBeanList the imageBeanList to set
	 */
	public void setImageBeanList(List<ImageBean> imageBeanList) {
		this.imageBeanList = imageBeanList;
	}
	
	
}
