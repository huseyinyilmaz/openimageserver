package ois;

import java.util.regex.Pattern;

import ois.controller.ControllerManager;
import ois.controller.impl.ControllerManagerImpl;
import ois.exceptions.InvalidNameException;
import ois.images.ImageManipulator;
import ois.images.impl.ImageManipulatorImpl;
import ois.model.ModelManager;
import ois.model.impl.ModelManagerImpl;
import ois.view.CSPageType;
import ois.view.CSParamType;

public class ApplicationManager {

	public static final String VERSION = "v0.5.167";
	
	public static final String NONE = "none";
	public static final String IMAGE_URI_PREFIX = "/ois/images/";
	public static final String MAIN_PAGE = "/ois/admin/main";
	public static final String IMAGE_UPLOAD_PAGE = "/ois/admin/imageupload";
	public static final String NUMBER_OF_IMAGES_PER_ROW = "3";
	//JSP URLS
	public static final String JSP_MAIN_PAGE_URL = "/ois/mainPage.jsp";
	public static final String JSP_ALBUM_EDIT_URL = "/ois/albumEdit.jsp";
	public static final String JSP_IMAGE_CREATE_URL = "/ois/imageCreate.jsp";
	public static final String JSP_IMAGE_EDIT_URL = "/ois/imageEdit.jsp";
	public static final String JSP_IMAGE_REVISIONS_URL = "/ois/imageRevisions.jsp";
	public static final String JSP_CREATE_REVISION_URL = "/ois/revisionCreate.jsp";

	private static ModelManager modelManager = new ModelManagerImpl();
	private static ImageManipulator manipulator = new ImageManipulatorImpl();
	private static ControllerManager controllerManager = new ControllerManagerImpl(modelManager);
	private static String serverURL = null;
	
	private static Exception nameException;
	
	public static ControllerManager getControllerManager(){
		return controllerManager;
	}
	
	public static ImageManipulator getManipulator(){
		if(manipulator == null)
			manipulator = new ImageManipulatorImpl();
		return manipulator;
	}
	public static String getServerURL(){
		if(serverURL == null)
			throw new IllegalArgumentException("Server url has to be set before it is retrived");
		return serverURL;
	}
	public static void setServerUrl(String serverURL){
		ApplicationManager.serverURL = serverURL;
	}
	public static String getHomeURL(){
		return MAIN_PAGE + "?" + CSParamType.PAGE.toString() + "=" + CSPageType.MAIN.toString();
	}
	
	public static void checkName(String name) throws InvalidNameException{
		if (name == null)
			throw new InvalidNameException("Name cannot be empty");
		if(!Pattern.matches("\\w+",name))
			throw new InvalidNameException("Name '" + name + "' is invalid. Name can only consist of alfa-numeric characters and '_' character");
	}

	public static Exception getNameException() {
		return nameException;
	}

	public static void setNameException(Exception nameException) {
		ApplicationManager.nameException = nameException;
	}
	
	
	
}
