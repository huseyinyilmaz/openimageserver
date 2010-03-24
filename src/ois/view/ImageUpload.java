package ois.view;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ois.ApplicationManager;
import ois.controller.Image;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;
import org.apache.commons.io.IOUtils;
@SuppressWarnings("serial")
public class ImageUpload extends HttpServlet {
  private static final Logger log =
      Logger.getLogger(ImageUpload.class.getName());

  public void doPost(HttpServletRequest req, HttpServletResponse res)
      throws ServletException, IOException {
	//Exception uploadException;
    try {
      ServletFileUpload upload = new ServletFileUpload();
      res.setContentType("text/plain");

      FileItemIterator iterator = upload.getItemIterator(req);
      String location = null;
      while (iterator.hasNext()) {
        FileItemStream item = iterator.next();
        InputStream stream = item.openStream();
        if (item.isFormField()) {
        	location = Streams.asString(stream);
        	log.info("Got a form field: " + item.getFieldName() + 
        		  	", value = " +location);
          
        } else {
        	byte[] byteArray = IOUtils.toByteArray(stream);
            log.info("Got an uploaded file: " + item.getFieldName() +
                      ", name = " + item.getName() +
                      ", type = " + item.getContentType() +
                      ", length = " + byteArray.length);
          Image img = new Image(item.getName(), location, byteArray,item.getContentType());
          ApplicationManager.getControllerManager().saveImage(img);
        }
      }
      res.sendRedirect("/images/upload.jsp");
    } catch (Exception ex) {
    	log.warning("An exception was caught. Exception = " + ex.getMessage());
    	//uploadException = ex;
/*
    	getServletContext().getRequestDispatcher("/edit.jsp").forward
        (req, res); 
*/
    	throw new ServletException(ex);
    }
  }
}