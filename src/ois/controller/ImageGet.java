package ois.controller;

import java.util.Date;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ois.ApplicationManager;
import ois.exceptions.PersistanceManagerException;

@SuppressWarnings("serial")
public class ImageGet extends HttpServlet {
  private static final Logger log =
      Logger.getLogger(ImageGet.class.getName());
  public long getLastModified(HttpServletRequest req)
  {
	  long modificationDate = req.getDateHeader("If-Modified-Since");
	  if(modificationDate>=0)
		  return modificationDate;
	  String uri = req.getRequestURI();
  	if ( uri.startsWith(ApplicationManager.IMAGE_URI_PREFIX) )
	  uri = uri.substring(ApplicationManager.IMAGE_URI_PREFIX.length());
	else{
		log.warning("URI is in wrong format.");
		return -1;
	}
  	Data data;
  	try {
  		data = ApplicationManager.getControllerManager().getImageData(uri);
  		modificationDate = data.getCreationDate().getTime();
  	} catch (PersistanceManagerException e) {
  		log.warning("Error while trying to get data from server. Will not return last modified date" );
  		e.printStackTrace();
  		modificationDate = -1;
  	}
  return modificationDate;
}
  public void doGet(HttpServletRequest req, HttpServletResponse res)
  throws ServletException {
	  try {
		  String uri = req.getRequestURI();
		  long modificationDate = req.getDateHeader("If-Modified-Since");
		  if(modificationDate != -1){
			  //return 304 not modified
			  log.info("Image is modified at " + new Date(modificationDate) +
					  ". Returning status code 304. URI = " + uri);
			  //res.addHeader("Cache-Control", "max-age=5184000");
			  //res.addDateHeader("Last-Modified", modificationDate);
			  //res.addDateHeader("Expires", Long.MAX_VALUE);
			  res.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
			  return;
		  }
		  if ( uri.startsWith(ApplicationManager.IMAGE_URI_PREFIX) )
			  uri = uri.substring(ApplicationManager.IMAGE_URI_PREFIX.length());
		  else{
			  //TODO throw an exception
		  }

		  Data data = ApplicationManager.getControllerManager().getImageData(uri);
		  if(data != null){
			  log.info("Returning image data. URI = " + uri + " Last-Modified = " + data.getCreationDate());
			  //set data
			  res.setContentType(data.getType());
			  res.getOutputStream().write(data.getData());
			  //res.addHeader("Cache-Control", "max-age=5184000");
			  //res.addDateHeader("Last-Modified", data.getCreationDate().getTime());
			  //res.addDateHeader("Expires", Long.MAX_VALUE);
			  res.setStatus(HttpServletResponse.SC_OK);
		  }else{
			  log.warning("Image not found. URI = " + uri);
			  res.setContentType("text/plain");
			  res.getWriter().println("no image found");
		  }

		  //res.sendRedirect("/images/upload.jsp");
	  } catch (Exception ex) {
		  log.warning("An exception was caught. Exception = " + ex.getMessage());
		  throw new ServletException(ex);
	  }
  }
}