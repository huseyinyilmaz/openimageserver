package ois.view;

import java.util.Date;
import java.util.List;

import ois.ApplicationManager;

public class ImageBean {
	private String	name;
	private String	description;
	private String	keyString;
	private String currentDataBeanKeyString;
	private DataBean currentDataBean = null;
	private Date	creationDate;
	private List<DataBean> dataBeanList;
	
	
	public ImageBean(){
		super();
	}
	
	public ImageBean(String keyString){
		super();
		this.keyString = keyString;
	}
	
	public DataBean getThumbnail(){
		DataBean thumbnail = null;
		for(DataBean dataBean:dataBeanList){
			if(dataBean.isThumbnail()==true){
				thumbnail = dataBean;
				break;
			}
		}
		return thumbnail;
	}
	
	public String getRevisionsLink(){
		return ApplicationManager.MAIN_PAGE + "?"
				+ CSParamType.PAGE.toString() + "=" + CSPageType.IMAGE_REVISIONS.toString()
			+ "&" + CSParamType.IMAGE.toString() + "=" + keyString;
	}

	public String getEditLink(){
		return ApplicationManager.MAIN_PAGE + "?"
				+ CSParamType.PAGE.toString() + "=" + CSPageType.IMAGE_EDIT.toString()
			+ "&" + CSParamType.IMAGE.toString() + "=" + keyString;
	}

	public String getRevisionCreateLink(){
		return ApplicationManager.MAIN_PAGE + "?"
		+ CSParamType.PAGE.toString() + "=" + CSPageType.REVISION_CREATE.toString()
	+ "&" + CSParamType.IMAGE.toString() + "=" + keyString;
	}
	
	public DataBean getCurrentDataBean(){
		if(currentDataBean == null)
			for(DataBean dataBean:dataBeanList)
				if(dataBean.getKeyString().equals(currentDataBeanKeyString))
					currentDataBean = dataBean;
		
		return currentDataBean;
	}
	
	public String getCreationDateString(){
		if (creationDate == null)
			return "deneme";
		return creationDate.toString();
	}
	
	/**
	 * @return the currentDataBeanKeyString
	 */
	public String getCurrentDataBeanKeyString() {
		return currentDataBeanKeyString;
	}

	/**
	 * @param currentDataBeanKeyString the currentDataBeanKeyString to set
	 */
	public void setCurrentDataBeanKeyString(String currentDataBeanKeyString) {
		this.currentDataBeanKeyString = currentDataBeanKeyString;
		currentDataBean = null;
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
	 * @return the imageDataSet
	 */
	public List<DataBean> getDataBeanList() {
		return dataBeanList;
	}

	/**
	 * @param imageDataSet the imageDataSet to set
	 */
	public void setDataBeanList(List<DataBean> imageDataList) {
		this.dataBeanList = imageDataList;
	}
	
	
}
