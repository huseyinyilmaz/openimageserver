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


@SuppressWarnings("serial")
public class ControllerServlet extends HttpServlet {
	private static final Logger log = Logger.getLogger(ControllerServlet.class.getName());
	
	
	/**
	 * Initialize main album page
	 * @param req current request object
	 * @param res current response object
	 * @throws IOException 
	 * @throws ServletException 
	 */
	private void initMain(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
    	
		List<Album> albums = ApplicationManager.getControllerManager().getAlbums();
		albums.add(0, new Album(-1,"All"));
		albums.add(0, new Album(0,"None"));
		req.setAttribute("albums",new AlbumsBean(albums));

		getServletContext().getRequestDispatcher("/main.jsp").forward(req, res); 
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
			switch (page){
			case IMAGE:
				break;
			case MAIN:
				initMain(req,res);
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
		if (name == null)
			throw new IllegalArgumentException("name cannot be null");
		String description = req.getParameter(CSParamType.DESCRIPTION.toString());
		ApplicationManager.getControllerManager().createAlbum(name, description);
		log.info("Album '" + name + "' was created");
	}
	
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
			case DELETE_ALBUM:
				deleteAlbum(req,res);
				break;
			}
			//TODO make sure that this works
			res.sendRedirect("/main?"+ CSParamType.PAGE.toString() + "=" + CSPageType.MAIN.toString());
			//getServletContext().getRequestDispatcher("/main?"+ CSParamType.PAGE.toString() + "=" + CSPageType.MAIN.toString()).forward(req, res);
		} catch (Exception ex) {
	    	log.warning("An exception was caught. Exception = " + ex.getMessage());
	    	throw new ServletException(ex);
	    }
		
	}
	
	
}
