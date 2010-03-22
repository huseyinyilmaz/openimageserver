package ois.view;

import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ois.controller.ControllerManager;
import ois.controller.Image;

@SuppressWarnings("serial")
public class ImageGet extends HttpServlet {
  private static final Logger log =
      Logger.getLogger(ImageGet.class.getName());

  public void doGet(HttpServletRequest req, HttpServletResponse res)
      throws ServletException {
	  try {
    	String uri = req.getRequestURI();
    	//TODO pictures has to be browser cached
    	/*
    	res.addHeader("Cache-Control", "max-age=30515108");
    	res.addDateHeader("Last-Modified", new Date("Thu, 01 Jan 2009 00:00:00 GMT").getTime() );
    	res.addDateHeader("Expires",  new Date("Sat, 26 Feb 2011 02:10:10 GMT").getTime() );
    	res.addDateHeader("Date",  new Date("Tue, 09 Mar 2010 22:09:48 GMT").getTime() );
    	*/
    	
    	if ( uri.startsWith(ControllerManager.IMAGE_URI_PREFIX) )
    		uri = uri.substring(ControllerManager.IMAGE_URI_PREFIX.length());
    	else{
    		//TODO throw an exception
    	}
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

    	Image file = ControllerManager.getImage(location,name);
    	if(file != null){
    		res.setContentType(file.getType());
    		res.getOutputStream().write(file.getData());
    	}else{
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