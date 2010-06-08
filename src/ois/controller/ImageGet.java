package ois.controller;

import java.util.Date;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ois.ApplicationManager;
import ois.exceptions.NoImageFoundException;
import ois.exceptions.PersistanceManagerException;

@SuppressWarnings("serial")
public class ImageGet extends HttpServlet {
  private static final Logger log =
      Logger.getLogger(ImageGet.class.getName());
  /* (non-Javadoc)
 * @see javax.servlet.http.HttpServlet#getLastModified(javax.servlet.http.HttpServletRequest)
 */
public long getLastModified(HttpServletRequest req)
  {
	  long modificationDate = req.getDateHeader("If-Modified-Since");
	  if(modificationDate>=0)
		  return modificationDate;
	  else
		  return new Date().getTime();
}
  /* (non-Javadoc)
 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
 */
public void doGet(HttpServletRequest req, HttpServletResponse res)
throws ServletException {
	try {
		String uri = req.getRequestURI();
		long modificationDate = req.getDateHeader("If-Modified-Since");
		//image has last modified date which means it is catched. return not modified.
		if(modificationDate != -1){
			//return 304 not modified
			log.info("Image has last modified date " + new Date(modificationDate) +
					". Returning status code 304. URI = " + uri);
			res.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
			return;
		}
		//remove prefix from uri which will leave only key string of image data.
		uri = uri.substring(ApplicationManager.IMAGE_URI_PREFIX.length());

		Data data;
		try {
			log.info("Get Image Data:Query for current Image. URI = " + uri);
			data = ApplicationManager.getControllerManager().getImageData(uri);
		} catch (PersistanceManagerException e) {
			log.warning("Error while trying to get data from server. Will not return last modified date" );
			e.printStackTrace();
			data = null;
		}catch(NoImageFoundException e){
			log.warning("Error while trying to get data from server. " + e.getMessage());
			e.printStackTrace();
			data = null;
		}

		if(data != null){
			log.info("Returning image data. URI = " + uri + " Last-Modified = " + data.getCreationDate());
			//set data
			res.setContentType(data.getType());
			res.getOutputStream().write(data.getData());
			res.setStatus(HttpServletResponse.SC_OK);
		}else{
			log.warning("Image not found. URI = " + uri);
			res.setContentType("text/plain");
			res.getWriter().println("no image found");
			res.setStatus(HttpServletResponse.SC_NOT_FOUND);
		}

	} catch (Exception ex) {
		log.warning("An exception was caught. Exception = " + ex.getMessage());
		throw new ServletException(ex);
	}
}
}