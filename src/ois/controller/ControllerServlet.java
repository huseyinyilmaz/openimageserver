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
import ois.exceptions.ImageDataTooBigException;
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
		String currentAlbumKeyString = req.getParameter(CSParamType.ALBUM.toString());
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
		//get Album id for clean state and exception state
		String albumKeyString = req.getParameter(CSParamType.ALBUM.toString());
		if(req.getAttribute("exception")==null){
			//clean state
			if (albumKeyString == null){
				//create album
				albumBean = new AlbumBean();
			}else{
				//edit album
				albumBean = ApplicationManager.getControllerManager().getAlbumBean(albumKeyString);
			}
		}else{
			//exception state
			albumBean = new AlbumBean();
			if (albumKeyString != null && albumKeyString.length()>0)
				albumBean.setKeyString(albumKeyString);
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
		ApplicationManager.getControllerManager().initImageCreate(this, req, res);
	}

	private void initImageEdit(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException, PersistanceManagerException{
		String imageKeyString = req.getParameter(CSParamType.IMAGE.toString());
		if(req.getAttribute("exception")== null){
			//clean state
			//String albumKeyString = req.getParameter(CSParamType.ALBUM.toString());
			Image image = ApplicationManager.getControllerManager().getImage(imageKeyString);

			AlbumBean albumBean = new AlbumBean();
			albumBean.setKeyString(image.getAlbum());

			ImageBean imageBean = new ImageBean();
			imageBean.setKeyString(image.getKeyString());
			imageBean.setName(image.getName());
			imageBean.setDescription(image.getDescription());
			imageBean.setCreationDate(image.getCreationDate());
			
			albumBean.setImageBeanList(new ArrayList<ImageBean>());
			albumBean.getImageBeanList().add(imageBean);
			albumBean.setCurrentImageKeyString(imageKeyString);
			
			req.setAttribute("albumBean",albumBean);
			log.info("Edit image page is being opened image name = " + image.getName() + 
														"\n image key = " + image.getKeyString() +
														"\n album key = " + image.getAlbum());
		}else{
			//exception state
			String albumKeyString = req.getParameter(CSParamType.ALBUM.toString());
			String name = req.getParameter(CSParamType.NAME.toString());
			String description = req.getParameter(CSParamType.DESCRIPTION.toString());

			
			AlbumBean albumBean = new AlbumBean();
			albumBean.setKeyString(albumKeyString);

			ImageBean imageBean = new ImageBean();
			imageBean.setKeyString(imageKeyString);
			imageBean.setName(name);
			imageBean.setDescription(description);
			//imageBean.setCreationDate(image.getCreationDate());
			
			albumBean.setImageBeanList(new ArrayList<ImageBean>());
			albumBean.getImageBeanList().add(imageBean);
			albumBean.setCurrentImageKeyString(imageKeyString);
			
			req.setAttribute("albumBean",albumBean);
		
		}
		forward(ApplicationManager.JSP_IMAGE_EDIT_URL,req,res);
	}
	
	
	private void initImageRevisions(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException, PersistanceManagerException{
		String imageFileKey = req.getParameter(CSParamType.IMAGE.toString());
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
		if(req.getAttribute("exception")==null){
			String imageFileKeyString = req.getParameter(CSParamType.IMAGE.toString());
			ImageBean imageBean = new ImageBean();
			imageBean.setKeyString(imageFileKeyString);
			imageBean.setDataBeanList(new ArrayList<DataBean>());
			DataBean dataBean = ApplicationManager.getControllerManager().getOriginalDataBean(imageFileKeyString);
			imageBean.getDataBeanList().add(dataBean);
			imageBean.setCurrentDataBeanKeyString(dataBean.getKeyString());
			req.setAttribute("imageBean",imageBean);
			log.info("Create revision page is being opened for image " + imageFileKeyString);
		}
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
			case IMAGE_REVISIONS:
				initImageRevisions(req,res);
				break;
			case IMAGE_EDIT:
				initImageEdit(req, res);
				break;
			case REVISION_CREATE:
				initRevisionCreate(req,res);
				break;
			}
		} catch (Exception e) {
	    	log.warning("An exception was caught. Exception = " + e);
	    	throw new ServletException(e);
	    }
	}

	/**
	 * Creates a new album.
	 * @param req current request object
	 * @param res current response object
	 * @return Key string of newly created album
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

	private String createRevision(HttpServletRequest req, HttpServletResponse res) throws PersistanceManagerException, ServletException, IOException{
		String imageKeyString = req.getParameter(CSParamType.IMAGE.toString());
		String widthString = req.getParameter(CSParamType.WIDTH.toString());
		String heightString = req.getParameter(CSParamType.HEIGHT.toString());
		String enhancedString = req.getParameter(CSParamType.ENHANCED.toString());//value is either null or "on"
		String imageDataKeyString = null;
		int width = Integer.parseInt(widthString);
		int height = Integer.parseInt(heightString);
		boolean isEnhanced = enhancedString != null;
		Data data = new Data();
		data.setWidth(width);
		data.setHeight(height);
		data.setEnhanced(isEnhanced);
		
		ImageBean imageBean = new ImageBean();
		imageBean.setKeyString(imageKeyString);
		imageBean.setDataBeanList(new ArrayList<DataBean>());
		DataBean dataBean = new DataBean();
		dataBean.setHeight(height);
		dataBean.setWidth(width);
		dataBean.setEnhanced(isEnhanced);
		dataBean.setKeyString("-1");
		imageBean.getDataBeanList().add(dataBean);
		imageBean.setCurrentDataBeanKeyString("-1");
		req.removeAttribute("imageBean");
		req.setAttribute("imageBean",imageBean);
		try{
			imageDataKeyString = ApplicationManager.getControllerManager().createImageData(imageKeyString, data);
		}catch(ImageDataTooBigException e){
			log.warning("ImageDataTooBigException occured: " + e.getMessage());
			req.setAttribute("exception",new Exception("Revision size exeeds limit : " + e.getMessage()));
			initRevisionCreate(req, res);
		}catch(PersistanceManagerException e){
			log.warning("PersistanceManagerException occured: " + e.getMessage());
			req.setAttribute("exception",e);
			initRevisionCreate(req, res);
		} catch(Exception e){
			log.warning("Unexpected exception occured: " + e);
			req.setAttribute("exception",new Exception("Unexpected exception occured:<br></br>"+e.getMessage()));
			initRevisionCreate(req, res);
		}
		
		log.info("New revision was created size=" + widthString + "x" + heightString 
										+ ",	enhanced=" + isEnhanced);
		return new DataBean(imageDataKeyString,imageKeyString).getViewLink();
	}

	
	
	/**
	 * modifies an existing album.
	 * @param req current request object
	 * @param res current response object
	 * @return Key String of current album
	 * @throws PersistanceManagerException 
	 * @throws IOException 
	 * @throws ServletException 
	 */
	private String editAlbum(HttpServletRequest req, HttpServletResponse res) throws PersistanceManagerException, ServletException, IOException{
		String name = req.getParameter(CSParamType.NAME.toString());
		String description = req.getParameter(CSParamType.DESCRIPTION.toString());
		String albumKeyString = req.getParameter(CSParamType.ALBUM.toString());
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

	private String editImage(HttpServletRequest req, HttpServletResponse res) throws PersistanceManagerException, ServletException, IOException{
		String name = req.getParameter(CSParamType.NAME.toString());
		String description = req.getParameter(CSParamType.DESCRIPTION.toString());
		String albumKeyString = req.getParameter(CSParamType.ALBUM.toString());
		String imageKeyString = req.getParameter(CSParamType.IMAGE.toString());
		Image image = new Image();
		image.setKeyString(imageKeyString);
		image.setName(name);
		image.setDescription(description);
		try{
			ApplicationManager.getControllerManager().saveImage(image);
		} catch(InvalidNameException e) {
			log.warning("InvalidNameException occured: " + e.getMessage());
			req.setAttribute("exception", e);
			initImageEdit(req, res);
		} catch(Exception e){
			log.warning("Unexpected exception occured: " + e.getMessage());
			req.setAttribute("exception",new Exception("Unexpected exception occured:<br></br>"+e.getMessage()));
			initImageEdit(req, res);
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
	private String deleteAlbum(HttpServletRequest req, HttpServletResponse res) throws PersistanceManagerException{
		String key = req.getParameter(CSParamType.ALBUM.toString());
		ApplicationManager.getControllerManager().deleteAlbum(key);
		return ApplicationManager.getHomeURL();
	}

	/**
	 * Deletes given album
	 * @param req current request object
	 * @param res current response object
	 * @throws PersistanceManagerException
	 */
	private String deleteImage(HttpServletRequest req, HttpServletResponse res) throws PersistanceManagerException{
		String key = req.getParameter(CSParamType.IMAGE.toString());
		String albumKeyString =  req.getParameter(CSParamType.ALBUM.toString());
		
		ApplicationManager.getControllerManager().deleteImageFile(key);
		return new AlbumBean(albumKeyString).getViewLink();
	}

	private String deleteRevision(HttpServletRequest req, HttpServletResponse res) throws PersistanceManagerException{
		String keyString = req.getParameter(CSParamType.REVISION.toString());
		String imageKeyString = req.getParameter(CSParamType.IMAGE.toString());
		ApplicationManager.getControllerManager().deleteImageData(keyString);
		return new ImageBean(imageKeyString).getRevisionsLink();
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
				url = deleteAlbum(req,res);
				break;
			case EDIT_IMAGE:
				url = editImage(req,res);
				break;
			case DELETE_IMAGE:
				url = deleteImage(req,res);
				break;
			case CREATE_REVISION:
				url = createRevision(req,res);
				break;
			case DELETE_REVISION:
				url =deleteRevision(req,res);
				break;
			}
			res.sendRedirect(url);
		} catch (Exception ex) {
	    	log.warning("An exception was caught. Exception = " + ex.getMessage());
	    	throw new ServletException(ex);
	    }
		
	}
	
	
}
