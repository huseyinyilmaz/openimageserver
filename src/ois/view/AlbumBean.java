package ois.view;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AlbumBean {
	private String keyString;
	private String name;
	private String description;
	private List<ImageBean> imageBeanList;

	public AlbumBean(){
		super();
	}
	public AlbumBean(String keyString,String name){
		super();
		this.name = name;
		this.keyString = keyString;
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
