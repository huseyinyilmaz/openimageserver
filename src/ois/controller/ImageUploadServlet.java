package ois.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ois.ApplicationManager;
import ois.exceptions.PersistanceManagerException;
import ois.view.CSParamType;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;
import org.apache.commons.io.IOUtils;
@SuppressWarnings("serial")
public class ImageUploadServlet extends HttpServlet {
	private static final Logger log =
		Logger.getLogger(ImageUploadServlet.class.getName());

	public void doPost(HttpServletRequest req, HttpServletResponse res)
    	throws ServletException, IOException {
		try{
			Image img = new Image();
			ServletFileUpload upload = new ServletFileUpload();

			FileItemIterator iterator = upload.getItemIterator(req);
			while (iterator.hasNext()) {
				FileItemStream item = iterator.next();
				InputStream stream = item.openStream();
				if (item.isFormField()) {
					String fieldValue = Streams.asString(stream);
					log.info("Got a form field: " + item.getFieldName() + 
							", value = " +fieldValue);
					//TODO imageName ve Description fieldlerini parameter type enumerationina ekle. burda ve jsp de degistir bu degerleri.
					if (item.getFieldName().equals(CSParamType.NAME.toString()))
						img.setName(fieldValue);
					else if(item.getFieldName().equals(CSParamType.DESCRIPTION.toString()) )
						img.setDescription(fieldValue);
					else if(item.getFieldName().equals(CSParamType.ITEM.toString()) )//ITEM field holds album id
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
			res.sendRedirect("/main?page=main&item="+img.getAlbum());
		} catch (FileUploadException e) {
			log.warning("FileUploadException was caught. Exception = " + e.getMessage());
			//TODO write also a message here
			throw new ServletException(e);
		} catch (IOException e) {
			log.warning("IOException was caught. Exception = " + e.getMessage());
			//TODO write also a message here
			throw new ServletException(e);
		} catch (PersistanceManagerException e) {
			// TODO Auto-generated catch block
			log.warning("Persistance manager exceptino was caught. Exception = " + e.getMessage());
			throw new ServletException(e);
		}

  }//doPost
}