package ois.controller;

import java.util.Date;

public class Data {
	private byte[] data;
	private String type;
	
	private int width;
	private int height;
	private boolean enhanced;
	
	private boolean original=false;
	private boolean thumbnail=false;
	
	private Date creationDate;
	
	public Data(){}
	public Data(Data orgData,byte[] data){
		this.data = data;
		this.original = orgData.isOriginal();
		this.type = orgData.getType();
		this.thumbnail = orgData.isThumbnail();
		this.creationDate = orgData.getCreationDate();
		this.enhanced = orgData.isEnhanced();
		this.width = orgData.getWidth();
		this.height = orgData.getHeight();
	}
	public Data(Data orgData){
		this.data = orgData.getData();
		this.original = orgData.isOriginal();
		this.type = orgData.getType();
		this.thumbnail = orgData.isThumbnail();
		this.creationDate = orgData.getCreationDate();
		this.enhanced = orgData.isEnhanced();
		this.width = orgData.getWidth();
		this.height = orgData.getHeight();
	}
	public Data(byte[] data){
		this.data = data;
	}
	
	
	public boolean isEnhanced() {
		return enhanced;
	}
	public void setEnhanced(boolean enhanced) {
		this.enhanced = enhanced;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public boolean isThumbnail() {
		return thumbnail;
	}
	public void setThumbnail(boolean thumbnail) {
		this.thumbnail = thumbnail;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public boolean isOriginal() {
		return original;
	}
	public void setOriginal(boolean original) {
		this.original = original;
	}
	public byte[] getData() {
		return data;
	}
	public void setData(byte[] data) {
		this.data = data;
	}
}
