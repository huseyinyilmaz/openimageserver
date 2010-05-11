package ois.view;

import java.util.List;

import ois.controller.Album;

/**
 * @author Huseyin
 * this class is used to pass values to albums.jsp page from controller servlet.
 * It holds album list , current album and image link list. bean is created in controller servlet and
 * read by albums.jsp
 */
public class MainPageBean {
	
	/**
	 * This list is uses controller album since it has
	 * same info with view album.
	 */
	private List<AlbumBean> albumBeanList;

	private String currentAlbumKeyString;

	/**
	 * Creates an empty albums bean
	 */
	public MainPageBean() {
		super();
	}
	
	/**
	 * Creates a new albums bean with given albums in it.
	 * @param albums List of albums that this bean will hold.
	 */
	public MainPageBean(List<AlbumBean> albumList) {
		super();
		this.albumBeanList = albumList;
	}

	

	/**
	 * Creates a new albums bean with given albums ,selected album and list of image links
	 * @param albums  List of albums that this bean will hold.
	 * @param imageLinks List of image links that this bean will hold
	 * @param currentAlbumId owner of images that image link is hold. this parameter has to be id of the owner album.
	 */
	public MainPageBean(List<AlbumBean> albumList,String currentAlbumKeyString) {
		this.albumBeanList = albumList;
		this.currentAlbumKeyString = currentAlbumKeyString;
	}

	
	public AlbumBean getCurrentAlbumBean(){
		AlbumBean currentAlbumBean = null;
		for(AlbumBean albumBean:albumBeanList){
			if(albumBean.getKeyString().equals(currentAlbumKeyString)){
				currentAlbumBean = albumBean;
				break;
			}
		}
		return currentAlbumBean;
	}
	
	
	/**
	 * @return the albumBeanList
	 */
	public List<AlbumBean> getAlbumBeanList() {
		return albumBeanList;
	}

	/**
	 * @param albumBeanList the albumBeanList to set
	 */
	public void setAlbumBeanList(List<AlbumBean> albumList) {
		this.albumBeanList = albumList;
	}

	/**
	 * @return the currentAlbumKeyString
	 */
	public String getCurrentAlbumKeyString() {
		return currentAlbumKeyString;
	}

	/**
	 * @param currentAlbumKeyString the currentAlbumKeyString to set
	 */
	public void setCurrentAlbumKeyString(String currentAlbumKeyString) {
		this.currentAlbumKeyString = currentAlbumKeyString;
	}
	
	

}
