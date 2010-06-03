package ois.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ois.ApplicationManager;
import ois.exceptions.EmptyImageDataException;
import ois.exceptions.ImageDataTooBigException;
import ois.exceptions.InvalidNameException;
import ois.exceptions.PersistanceManagerException;
import ois.view.AlbumBean;
import ois.view.CSParamType;
import ois.view.ImageBean;

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

	public void setAttributes(Image image, Exception e,HttpServletRequest req){
		String temproryImageBeanKeyString = "-1";
		req.setAttribute("exception", e);
		AlbumBean albumBean = new AlbumBean();
		albumBean.setKeyString(image.getAlbum());
		ImageBean imageBean = new ImageBean();
		//this is a new image so it does not have any keyString
		imageBean.setKeyString(temproryImageBeanKeyString);
		imageBean.setName(image.getName());
		imageBean.setDescription(image.getDescription());
		albumBean.setImageBeanList(new ArrayList<ImageBean>());
		albumBean.getImageBeanList().add(imageBean);
		//set temprory image key string, So we can find current image
		albumBean.setCurrentImageKeyString(temproryImageBeanKeyString);
		req.setAttribute("albumBean", albumBean);
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse res)
    	throws ServletException, IOException {
		ServletFileUpload upload = new ServletFileUpload();
		Image img = new Image();
		try{
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
					else if(item.getFieldName().equals(CSParamType.ALBUM.toString()) )//ITEM field holds album id
						img.setAlbum(fieldValue);
				} else {
					byte[] byteArray = IOUtils.toByteArray(stream);
					log.info("Got an uploaded file: " + item.getFieldName() +
							", name = " + item.getName() +
							", type = " + item.getContentType() +
							", length = " + byteArray.length);
					img.getDataList().add(new Data(byteArray));
					img.setType(item.getContentType());
				}
			}
			//create Image
			ApplicationManager.getControllerManager().createImage(img);
			res.sendRedirect(new AlbumBean(img.getAlbum()).getViewLink());
		}catch (InvalidNameException e){
			log.warning("InvalidNameException caught :" + e.getMessage());
			//add request parameters that will be used in jsp
			setAttributes(img,e,req);
			ApplicationManager.getControllerManager().initImageCreate(this, req, res);
		}catch (FileUploadException e){
			log.warning("FileUploadException was caught. Exception = " + e.getMessage());
			//add request parameters that will be used in jsp
			setAttributes(img,e,req);
			ApplicationManager.getControllerManager().initImageCreate(this, req, res);
		} catch (IOException e) {
			log.warning("IOException was caught. Exception = " + e.getMessage());
			//add request parameters that will be used in jsp
			setAttributes(img,e,req);
			ApplicationManager.getControllerManager().initImageCreate(this, req, res);
		} catch (PersistanceManagerException e) {
			log.warning("Persistance manager exceptino was caught. Exception = " + e.getMessage());
			//add request parameters that will be used in jsp
			setAttributes(img,e,req);
			ApplicationManager.getControllerManager().initImageCreate(this, req, res);
		} catch (ImageDataTooBigException e) {
			log.warning("ImageDataTooBigException was caught. Exception = " + e.getMessage());
			//add request parameters that will be used in jsp
			setAttributes(img,e,req);
			ApplicationManager.getControllerManager().initImageCreate(this, req, res);
		} catch (EmptyImageDataException e) {
			log.warning("EmptyImageDataException was caught. Exception = " + e.getMessage());
			//add request parameters that will be used in jsp
			setAttributes(img,e,req);
			ApplicationManager.getControllerManager().initImageCreate(this, req, res);
		} 

  }//doPost
}