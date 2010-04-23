package ois.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;
import org.apache.commons.io.IOUtils;

import ois.ApplicationManager;
import ois.exceptions.PersistanceManagerException;
import ois.view.AlbumsBean;
import ois.view.CSActionType;
import ois.view.CSPageType;
import ois.view.CSParamType;
import ois.view.ImageLink;

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
		AlbumsBean albumsBean = new AlbumsBean(); 
		// Get album key 
		String albumKey = req.getParameter(CSParamType.ITEM.toString());
    	// Default behavior for albums are to select none node 
		if (albumKey == null)
    		albumKey = ApplicationManager.ALBUMNODE_NONE;
		log.info("Album key to initialize = " + albumKey);
    	List<ImageLink> imageLinks = ApplicationManager.getControllerManager().getImageLinks(albumKey);
    	log.info("Image Links was came back from contoller link size = " + imageLinks.size());
    	List<Album> albums = ApplicationManager.getControllerManager().getAlbums();
		albums.add(0, new Album(ApplicationManager.ALBUMNODE_ALL,ApplicationManager.ALBUMNODE_ALL));
		albums.add(0, new Album(ApplicationManager.ALBUMNODE_NONE,ApplicationManager.ALBUMNODE_NONE));
		
		albumsBean.setAlbums(albums);
		albumsBean.setImageLinks(imageLinks);
		albumsBean.setCurrentAlbumKey(albumKey);
		
		req.setAttribute("albums",albumsBean);
		
		forward("/albums.jsp",req, res);
	}

	private void forward(String context, HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
		ApplicationManager.getControllerManager().close();
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

		Album album;
		String albumKey = req.getParameter(CSParamType.ITEM.toString());
		if (albumKey == null){
			album = new Album();
		}else{
			album = ApplicationManager.getControllerManager().getAlbum(albumKey);
		}
		req.setAttribute("album",album);
		forward("/albumEdit.jsp",req,res);
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
		String albumKey = req.getParameter(CSParamType.ITEM.toString());
		Album album = new Album();
		album.setKey(albumKey);
		req.setAttribute("album",album);
		log.info("Image create page is being opened for album " + albumKey );
		forward("/imageCreate.jsp",req,res);
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
		String key = req.getParameter(CSParamType.ITEM.toString());
		Album album = new Album();
		album.setKey(key);
		album.setName(name);
		album.setDescription(description);
		ApplicationManager.getControllerManager().saveAlbum(album);
		log.info("Album '" + name + "' was saved");
	}

	
	/**
	 * creates a new imagefile and creates original image data
	 * @param req current request object
	 * @param res current response object
	 * @throws PersistanceManagerException
	 * @throws ServletException 
	 */
	private void createImage(HttpServletRequest req, HttpServletResponse res) throws PersistanceManagerException, ServletException{
	    //TODO this was carried to different servlet. this method and its action type has to be removed
		try {
	      Image img = new Image();
	      ServletFileUpload upload = new ServletFileUpload();
	      res.setContentType("text/plain");

	      FileItemIterator iterator = upload.getItemIterator(req);
	      while (iterator.hasNext()) {
	        FileItemStream item = iterator.next();
	        InputStream stream = item.openStream();
	        if (item.isFormField()) {
	        	String fieldValue = Streams.asString(stream);
	        	log.info("Got a form field: " + item.getFieldName() + 
	        		  	", value = " +fieldValue);
	        	//TODO imageName ve Description fieldlerini paramether type enumerationina ekle. burda ve jsp de degistir bu degerleri.
	        	if (item.getFieldName() == CSParamType.NAME.toString())
	        		img.setName(fieldValue);
	        	else if(item.getFieldName() == CSParamType.DESCRIPTION.toString())
	        		img.setDescription(fieldValue);
	        	else if(item.getFieldName() == CSParamType.ITEM.toString())//ITEM field hods album id
	        		img.setAlbum(Long.parseLong(fieldValue));
	        } else {
	        	byte[] byteArray = IOUtils.toByteArray(stream);
	            log.info("Got an uploaded file: " + item.getFieldName() +
	                      ", name = " + item.getName() +
	                      ", type = " + item.getContentType() +
	                      ", length = " + byteArray.length);
	          img.setData(byteArray);
	          img.setType(item.getContentType());
	          ApplicationManager.getControllerManager().createImage(img);
	        }
	      }
	      res.sendRedirect("/images/upload.jsp");
	    } catch (FileUploadException ex) {
	    	log.warning("An exception was caught. Exception = " + ex.getMessage());
	    	//TODO write also a message here
	    	throw new ServletException(ex);
	    }catch (IOException ex) {
			log.warning("An exception was caught. Exception = " + ex.getMessage());
			//TODO write also a message here
			throw new ServletException(ex);
		}
	    
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
			case CREATE_IMAGE:
				createImage(req,res);
				//TODO bu islemden sonra uzerinde bulunulan albumun sayfasina donulmeli.
				break;
			}
			ApplicationManager.getControllerManager().close();
			res.sendRedirect("/main?"+ CSParamType.PAGE.toString() + "=" + CSPageType.MAIN.toString());
		} catch (Exception ex) {
	    	log.warning("An exception was caught. Exception = " + ex.getMessage());
	    	throw new ServletException(ex);
	    }
		
	}
	
	
}
