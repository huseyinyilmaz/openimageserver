package ois.controller;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ois.ApplicationManager;
import ois.exceptions.PersistanceManagerException;
import ois.view.AlbumBean;
import ois.view.CSActionType;
import ois.view.CSPageType;
import ois.view.CSParamType;
import ois.view.DataBean;
import ois.view.ImageBean;
import ois.view.MainPageBean;

@SuppressWarnings("serial")
public class ControllerServlet extends HttpServlet {
	private static final Logger log = Logger.getLogger(ControllerServlet.class.getName());
	
	
	/**
	 * Initialize main album page
	 * @param req current request object
	 * @param res current response object
	 * @throws IOException 
	 * @throws ServletException 
	 * @throws PersistanceManagerException 
	 * @throws NumberFormatException 
	 */
	private void initMain(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException, NumberFormatException, PersistanceManagerException{
    	log.info("Initializing main album page");
		MainPageBean mainPageBean = new MainPageBean(); 
		// Get album key 
		String currentAlbumKeyString = req.getParameter(CSParamType.ITEM.toString());
    	// Default behavior for albums are to select none node 
		if (currentAlbumKeyString == null)
    		currentAlbumKeyString = ApplicationManager.NONE;
		log.info("Album key to initialize = " + currentAlbumKeyString);
    	List<ImageBean> imageBeanList = ApplicationManager.getControllerManager().getImageBeanList(currentAlbumKeyString);
    	log.info("Image Beans was came back from contoller number of beans are " + imageBeanList.size());
    	List<AlbumBean> albumBeanList = ApplicationManager.getControllerManager().getAlbumBeanList();
    	for(AlbumBean albumBean:albumBeanList){
    		if (albumBean.getKeyString().equals(currentAlbumKeyString)){
    			albumBean.setImageBeanList(imageBeanList);
    			break;
    		}
    	}
		albumBeanList.add(0, new AlbumBean(ApplicationManager.NONE,"None"));
		mainPageBean.setAlbumBeanList(albumBeanList);
		mainPageBean.setCurrentAlbumKeyString(currentAlbumKeyString);
		
		req.setAttribute("mainPageBean",mainPageBean);
		
		forward(ApplicationManager.JSP_MAIN_PAGE_URL,req, res);
	}

	private void forward(String context, HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
		getServletContext().getRequestDispatcher(context).forward(req, res); 
	}
	/**
	 * Initialize album edit page
	 * @param req current request object
	 * @param res current response object
	 * @throws IOException 
	 * @throws ServletException 
	 * @throws PersistanceManagerException 
	 */
	private void initAlbumEdit(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException, PersistanceManagerException{

		AlbumBean albumBean;
		String albumKeyString = req.getParameter(CSParamType.ITEM.toString());
		if (albumKeyString == null){
			albumBean = new AlbumBean();
		}else{
			albumBean = ApplicationManager.getControllerManager().getAlbumBean(albumKeyString);
		}
		req.setAttribute("albumBean",albumBean);
		forward(ApplicationManager.JSP_ALBUM_EDIT_URL,req,res);
	}

	
	/**
	 * Initialize image create page
	 * @param req current request object
	 * @param res current response object
	 * @throws ServletException
	 * @throws IOException
	 * @throws PersistanceManagerException
	 */
	private void initImageCreate(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException, PersistanceManagerException{
		String albumKeyString = req.getParameter(CSParamType.ITEM.toString());
		AlbumBean albumBean = new AlbumBean();
		albumBean.setKeyString(albumKeyString);
		req.setAttribute("albumBean",albumBean);
		log.info("Image create page is being opened for album " + albumKeyString );
		forward(ApplicationManager.JSP_IMAGE_CREATE_URL,req,res);
	}

	private void initImageEdit(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException, PersistanceManagerException{
		String imageFileKey = req.getParameter(CSParamType.ITEM.toString());
		ImageBean imageBean = ApplicationManager.getControllerManager().getImageBean(imageFileKey);
		imageBean.setCurrentDataBeanKeyString(ApplicationManager.NONE);
		imageBean.getDataBeanList().add(0,new DataBean(ApplicationManager.NONE,imageFileKey));
		req.setAttribute("imageBean",imageBean);
		log.info("Revisions page is being opened for image " + imageFileKey + "(" + imageBean.getName() + ")" );
		forward(ApplicationManager.JSP_IMAGE_REVISIONS_URL,req,res);
	}

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res)
				throws ServletException, IOException {
		try {
			CSPageType page = CSPageType.fromString(req.getParameter(CSParamType.PAGE.toString()));
			if (page == null)
				throw new IllegalArgumentException("'page' paramether does not an expected value. call page with query string ?"+CSParamType.PAGE.toString()+"="+CSPageType.MAIN.toString());
			log.info("Got page type " + page);
			switch (page){
			case IMAGE:
				break;
			case MAIN:
				initMain(req,res);
				break;
			case ALBUM_EDIT:
				initAlbumEdit(req,res);
				break;
			case IMAGE_CREATE:
				initImageCreate(req,res);
				break;
			case IMAGE_EDIT:
				initImageEdit(req,res);
				break;
			}
		} catch (Exception ex) {
	    	log.warning("An exception was caught. Exception = " + ex.getMessage());
	    	throw new ServletException(ex);
	    }
	}

	/**
	 * Creates a new album.
	 * @param req current request object
	 * @param res current response object
	 * @throws PersistanceManagerException 
	 */
	private void createAlbum(HttpServletRequest req, HttpServletResponse res) throws PersistanceManagerException{
		String name = req.getParameter(CSParamType.NAME.toString());
		//TODO move this to name check
		if (name == null)
			throw new IllegalArgumentException("name cannot be null");
		String description = req.getParameter(CSParamType.DESCRIPTION.toString());
		ApplicationManager.getControllerManager().createAlbum(name, description);
		log.info("Album '" + name + "' was created");
	}

	
	
	/**
	 * modifys an existing album.
	 * @param req current request object
	 * @param res current response object
	 * @throws PersistanceManagerException 
	 */
	private void editAlbum(HttpServletRequest req, HttpServletResponse res) throws PersistanceManagerException{
		String name = req.getParameter(CSParamType.NAME.toString());
		//TODO move this to name check
		if (name == null)
			throw new IllegalArgumentException("name cannot be null");
		String description = req.getParameter(CSParamType.DESCRIPTION.toString());
		String albumKeyString = req.getParameter(CSParamType.ITEM.toString());
		Album album = new Album();
		album.setKeyString(albumKeyString);
		album.setName(name);
		album.setDescription(description);
		ApplicationManager.getControllerManager().saveAlbum(album);
		log.info("Album '" + name + "' was saved");
	}

	/**
	 * Deletes given album
	 * @param req current request object
	 * @param res current response object
	 * @throws PersistanceManagerException
	 */
	private void deleteAlbum (HttpServletRequest req, HttpServletResponse res) throws PersistanceManagerException{
		String key = req.getParameter(CSParamType.ITEM.toString());
		ApplicationManager.getControllerManager().deleteAlbum(key);
	}

	/**
	 * Deletes given album
	 * @param req current request object
	 * @param res current response object
	 * @throws PersistanceManagerException
	 */
	private void deleteImage (HttpServletRequest req, HttpServletResponse res) throws PersistanceManagerException{
		String key = req.getParameter(CSParamType.ITEM.toString());
		ApplicationManager.getControllerManager().deleteImageFile(key);
	}

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		try {
			if (req.getQueryString() != null )
				doGet(req,res);
			String actionStr = req.getParameter(CSParamType.ACTION.toString());
			CSActionType action = CSActionType.fromString(actionStr);
			if (action == null)
				throw new IllegalArgumentException("'action' paramether does not have an expected value. action =" + actionStr );
			log.info("Got action type " + action.toString());
			switch(action){
			case CREATE_ALBUM:
				createAlbum(req,res);
				break;
			case EDIT_ALBUM:
				editAlbum(req,res);
				break;
			case DELETE_ALBUM:
				deleteAlbum(req,res);
				break;
			case DELETE_IMAGE:
				deleteImage(req,res);
			}
			res.sendRedirect("/main?"+ CSParamType.PAGE.toString() + "=" + CSPageType.MAIN.toString());
		} catch (Exception ex) {
	    	log.warning("An exception was caught. Exception = " + ex.getMessage());
	    	throw new ServletException(ex);
	    }
		
	}
	
	
}
