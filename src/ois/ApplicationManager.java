package ois;

import ois.controller.ControllerManager;
import ois.controller.impl.ControllerManagerImpl;
import ois.images.ImageManipulator;
import ois.images.impl.ImageManipulatorImpl;
import ois.model.ModelManager;
import ois.model.impl.ModelManagerImpl;

public class ApplicationManager {
	
	public static final String NONE = "none";
	public static final String IMAGE_URI_PREFIX = "/ois/images/";
	public static final String MAIN_PAGE = "/main";
	public static final String IMAGE_UPLOAD_PAGE = "/imageupload";
	
	//JSP URLS
	public static final String JSP_MAIN_PAGE_URL = "/ois/mainPage.jsp";
	public static final String JSP_ALBUM_EDIT_URL = "/ois/albumEdit.jsp";
	
	public static final String JSP_IMAGE_CREATE_URL = "/ois/imageCreate.jsp";
	public static final String JSP_IMAGE_REVISIONS_URL = "/ois/imageRevisions.jsp";
	
	private static ModelManager modelManager = new ModelManagerImpl();
	private static ImageManipulator manipulator = new ImageManipulatorImpl();
	private static ControllerManager controllerManager = new ControllerManagerImpl(modelManager);
	
	public static ControllerManager getControllerManager(){
		return controllerManager;
	}
	
	public static ImageManipulator getManipulator(){
		if(manipulator == null)
			manipulator = new ImageManipulatorImpl();
		return manipulator;
	}
	
}
