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
		
    	// Get album id.
    	// if album id is selected we have to send image infos to client
    	String albumIdStr = req.getParameter(CSParamType.ITEM.toString());
		long albumId = 0;//if no album is selected value has to be 0
		if(albumIdStr != null)
			try{
				albumId = Long.valueOf(albumIdStr);
			}catch(NumberFormatException e){
				throw new IllegalArgumentException(albumIdStr +" is not a valid id. Id has to be a number",e);
			}
		log.info("Album Id to initialize = " + albumId);
    	List<ImageLink> imageLinks = ApplicationManager.getControllerManager().getImageLinks(albumId);
    	log.info("Image Links was came back forom contoller link size = " + albumId);
    	List<Album> albums = ApplicationManager.getControllerManager().getAlbums();
		albums.add(0, new Album(-1,"All"));
		albums.add(0, new Album(0,"None"));
		
		albumsBean.setAlbums(albums);
		albumsBean.setImageLinks(imageLinks);
		albumsBean.setCurrentAlbumId(albumId);
		
		req.setAttribute("albums",albumsBean);

		
		
		getServletContext().getRequestDispatcher("/albums.jsp").forward(req, res); 
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
		String albumIdStr = req.getParameter(CSParamType.ITEM.toString());
		if (albumIdStr == null){
			album = new Album();
		}else{
			long albumId = Long.parseLong(albumIdStr);
			album = ApplicationManager.getControllerManager().getAlbum(albumId);
		}
		req.setAttribute("album",album);
		getServletContext().getRequestDispatcher("/albumEdit.jsp").forward(req, res); 
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
		long albumId = Long.parseLong( req.getParameter(CSParamType.ITEM.toString()) );
		Album album = new Album();
		album.setId(albumId);
		
		req.setAttribute("album",album);
		log.info("Image create page is being opened for album " + Long.toString( album.getId()) );
		getServletContext().getRequestDispatcher("/imageCreate.jsp").forward(req, res);
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
		long id = Long.parseLong(req.getParameter(CSParamType.ITEM.toString()));
		Album album = new Album();
		album.setId(id);
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
		//Exception uploadException;
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
	        	if (item.getFieldName() == "imageName")
	        		img.setName(fieldValue);
	        	else if(item.getFieldName() == "imageDescription")
	        		img.setDescription(fieldValue);
	        	else if(item.getFieldName() == CSParamType.ITEM.toString())
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
		Long id = Long.valueOf(req.getParameter(CSParamType.ITEM.toString()));
		ApplicationManager.getControllerManager().deleteAlbum(id);
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
			CSActionType action = CSActionType.fromString(req.getParameter(CSParamType.ACTION.toString()));
			if (action == null)
				throw new IllegalArgumentException("'action' paramether does not an expected value.");
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

			res.sendRedirect("/main?"+ CSParamType.PAGE.toString() + "=" + CSPageType.MAIN.toString());
		} catch (Exception ex) {
	    	log.warning("An exception was caught. Exception = " + ex.getMessage());
	    	throw new ServletException(ex);
	    }
		
	}
	
	
}
