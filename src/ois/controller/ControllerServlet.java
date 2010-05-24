package ois.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ois.ApplicationManager;
import ois.exceptions.InvalidNameException;
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
		String albumKeyString = null;
		if(req.getAttribute("exception")==null){
			albumKeyString = req.getParameter(CSParamType.ITEM.toString());
			if (albumKeyString == null){
				albumBean = new AlbumBean();
			}else{
				albumBean = ApplicationManager.getControllerManager().getAlbumBean(albumKeyString);
			}
		}else{
			albumBean = new AlbumBean();
			albumBean.setName(req.getParameter(CSParamType.NAME.toString()));
			albumBean.setDescription(req.getParameter(CSParamType.DESCRIPTION.toString()));
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
		/*
		String albumKeyString = req.getParameter(CSParamType.ITEM.toString());
		AlbumBean albumBean = new AlbumBean();
		albumBean.setKeyString(albumKeyString);
		req.setAttribute("albumBean",albumBean);
		log.info("Image create page is being opened for album " + albumKeyString );
		forward(ApplicationManager.JSP_IMAGE_CREATE_URL,req,res);
		*/
		ApplicationManager.getControllerManager().initImageCreate(this, req, res);
	}

	private void initImageEdit(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException, PersistanceManagerException{
		String imageFileKey = req.getParameter(CSParamType.ITEM.toString());
		String imageDataKey = req.getParameter(CSParamType.REVISION.toString());
		String url = req.getRequestURL().substring(0, req.getRequestURL().length()-ApplicationManager.MAIN_PAGE.length());
		ApplicationManager.setServerUrl(url);
		if(imageDataKey == null)
			imageDataKey = ApplicationManager.NONE;
		AlbumBean albumBean = ApplicationManager.getControllerManager().getImageBean(imageFileKey);
		albumBean.getCurrentImageBean().setCurrentDataBeanKeyString(imageDataKey);
		
		albumBean.getCurrentImageBean().getDataBeanList().add(0,new DataBean(ApplicationManager.NONE,imageFileKey));
		req.setAttribute("albumBean",albumBean);
		log.info("Revisions page is being opened for image " + imageFileKey + "(" + albumBean.getCurrentImageBean().getName() + ")" );
		forward(ApplicationManager.JSP_IMAGE_REVISIONS_URL,req,res);
	}

	private void initRevisionCreate(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException, PersistanceManagerException{
		String imageFileKeyString = req.getParameter(CSParamType.ITEM.toString());
		ImageBean imageBean = new ImageBean();
		imageBean.setKeyString(imageFileKeyString);
		imageBean.setDataBeanList(new ArrayList<DataBean>());
		DataBean dataBean = ApplicationManager.getControllerManager().getOriginalDataBean(imageFileKeyString);
		imageBean.getDataBeanList().add(dataBean);
		imageBean.setCurrentDataBeanKeyString(dataBean.getKeyString());
		req.setAttribute("imageBean",imageBean);
		log.info("Create revision page is being opened for image " + imageFileKeyString);
		forward(ApplicationManager.JSP_CREATE_REVISION_URL,req,res);
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
			case REVISION_CREATE:
				initRevisionCreate(req,res);
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
	 * @return TODO
	 * @throws PersistanceManagerException 
	 * @throws IOException 
	 * @throws ServletException 
	 */
	private String createAlbum(HttpServletRequest req, HttpServletResponse res) throws PersistanceManagerException, ServletException, IOException{
		String name = req.getParameter(CSParamType.NAME.toString());
		String albumKeyString = null;
		String description = req.getParameter(CSParamType.DESCRIPTION.toString());
		try {
			albumKeyString = ApplicationManager.getControllerManager().createAlbum(name, description);
		} catch(InvalidNameException e) {
			log.warning("InvalidNameException occured: " + e.getMessage());
			req.setAttribute("exception", e);
			initAlbumEdit(req, res);
		} catch(PersistanceManagerException e){
			log.warning("PersistanceManagerException occured: " + e.getMessage());
			req.setAttribute("exception",e);
			initAlbumEdit(req, res);
		} catch(Exception e){
			log.warning("Unexpected exception occured: " + e.getMessage());
			req.setAttribute("exception",new Exception("Unexpected exception occured:<br></br>"+e.getMessage()));
			initAlbumEdit(req, res);
		}
		log.info("Album '" + name + "' was created");
		return new AlbumBean(albumKeyString,name).getViewLink();
	}

	private void createRevision(HttpServletRequest req, HttpServletResponse res) throws PersistanceManagerException, ServletException, IOException{
		String imageKeyString = req.getParameter(CSParamType.ITEM.toString());
		String widthString = req.getParameter(CSParamType.WIDTH.toString());
		String heightString = req.getParameter(CSParamType.HEIGHT.toString());
		String enhancedString = req.getParameter(CSParamType.ENHANCED.toString());//value is either null or "on"
		
		int width = Integer.parseInt(widthString);
		int height = Integer.parseInt(heightString);
		boolean isEnhanced = enhancedString != null;
		Data data = new Data();
		data.setWidth(width);
		data.setHeight(height);
		data.setEnhanced(isEnhanced);
		try{
		ApplicationManager.getControllerManager().createImageData(imageKeyString, data);
		} catch(PersistanceManagerException e){
			log.warning("PersistanceManagerException occured: " + e.getMessage());
			req.setAttribute("exception",e);
			initRevisionCreate(req, res);
		} catch(Exception e){
			log.warning("Unexpected exception occured: " + e.getMessage());
			req.setAttribute("exception",new Exception("Unexpected exception occured:<br></br>"+e.getMessage()));
			initRevisionCreate(req, res);
		}
		
		log.info("New revision was created size=" + widthString + "x" + heightString 
										+ ",	enhanced=" + isEnhanced);
	}

	
	
	/**
	 * modifies an existing album.
	 * @param req current request object
	 * @param res current response object
	 * @return TODO
	 * @throws PersistanceManagerException 
	 * @throws IOException 
	 * @throws ServletException 
	 */
	private String editAlbum(HttpServletRequest req, HttpServletResponse res) throws PersistanceManagerException, ServletException, IOException{
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
		try{
		ApplicationManager.getControllerManager().saveAlbum(album);
		} catch(InvalidNameException e) {
			log.warning("InvalidNameException occured: " + e.getMessage());
			req.setAttribute("exception", e);
			initAlbumEdit(req, res);
		} catch(Exception e){
			log.warning("Unexpected exception occured: " + e.getMessage());
			req.setAttribute("exception",new Exception("Unexpected exception occured:<br></br>"+e.getMessage()));
			initAlbumEdit(req, res);
		}

		log.info("Album '" + name + "' was saved");
		return new AlbumBean(albumKeyString).getViewLink();
	}

	/**
	 * Deletes given album
	 * @param req current request object
	 * @param res current response object
	 * @throws PersistanceManagerException
	 */
	private void deleteAlbum(HttpServletRequest req, HttpServletResponse res) throws PersistanceManagerException{
		String key = req.getParameter(CSParamType.ITEM.toString());
		ApplicationManager.getControllerManager().deleteAlbum(key);
	}

	/**
	 * Deletes given album
	 * @param req current request object
	 * @param res current response object
	 * @throws PersistanceManagerException
	 */
	private void deleteImage(HttpServletRequest req, HttpServletResponse res) throws PersistanceManagerException{
		String key = req.getParameter(CSParamType.ITEM.toString());
		ApplicationManager.getControllerManager().deleteImageFile(key);
	}

	private void deleteRevision(HttpServletRequest req, HttpServletResponse res) throws PersistanceManagerException{
		String key = req.getParameter(CSParamType.ITEM.toString());
		ApplicationManager.getControllerManager().deleteImageData(key);
	}

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		try {
			/*
			if (req.getQueryString() != null )
				doGet(req,res);
			*/
			String actionStr = req.getParameter(CSParamType.ACTION.toString());
			CSActionType action = CSActionType.fromString(actionStr);
			if (action == null)
				throw new IllegalArgumentException("'action' paramether does not have an expected value. action =" + actionStr );
			log.info("Got action type " + action.toString());
			String url=null;
			switch(action){
			case CREATE_ALBUM:
				url = createAlbum(req,res);
				break;
			case EDIT_ALBUM:
				url = editAlbum(req,res);
				break;
			case DELETE_ALBUM:
				deleteAlbum(req,res);
				break;
			case DELETE_IMAGE:
				deleteImage(req,res);
				break;
			case CREATE_REVISION:
				createRevision(req,res);
				break;
			case DELETE_REVISION:
				deleteRevision(req,res);
				break;
			}
			if(url==null)
				//if there is no url provided return to main page
				res.sendRedirect("/main?"+ CSParamType.PAGE.toString() + "=" + CSPageType.MAIN.toString());
			else
				res.sendRedirect(url);
		} catch (Exception ex) {
	    	log.warning("An exception was caught. Exception = " + ex.getMessage());
	    	throw new ServletException(ex);
	    }
		
	}
	
	
}
