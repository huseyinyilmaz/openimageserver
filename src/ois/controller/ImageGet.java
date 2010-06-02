package ois.controller;

import java.util.Date;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ois.ApplicationManager;

@SuppressWarnings("serial")
public class ImageGet extends HttpServlet {
  private static final Logger log =
      Logger.getLogger(ImageGet.class.getName());

  public void doGet(HttpServletRequest req, HttpServletResponse res)
      throws ServletException {
	  try {
    	String uri = req.getRequestURI();
    	long modificationDate = req.getDateHeader("if-modified-since");
    	if(modificationDate != -1){
    		//return 304 not modified
    		log.info("Image is modified at " + new Date(modificationDate) +
    				". Returning status code 304. URI = " + uri);
    		res.setStatus(304);
    		return;
    	}
    	//TODO Image_uri_prefix i degistirdin dogru degistirilip degistirilmaediginden emin ol.
    	if ( uri.startsWith(ApplicationManager.IMAGE_URI_PREFIX) )
    		uri = uri.substring(ApplicationManager.IMAGE_URI_PREFIX.length());
    	else{
    		//TODO throw an exception
    	}
    	/*
    	int slashLocation = uri.lastIndexOf('/');
    	String name;
    	String location;
    	if (slashLocation != -1){
    		name = uri.substring(slashLocation+1);
    		location = uri.substring(0,slashLocation);
    	}else{
    		name = uri;
    		location = null;
    	}
    	 */
    	Data data = ApplicationManager.getControllerManager().getImageData(uri);
    	if(data != null){
    		log.info("Returning image data. URI = " + uri);
    		//set data
    		res.setContentType(data.getType());
    		res.getOutputStream().write(data.getData());
        	res.addHeader("Cache-Control", "max-age=0");
        	res.addDateHeader("Last-Modified", data.getCreationDate().getTime());
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