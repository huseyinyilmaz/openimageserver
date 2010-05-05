package ois;

import ois.controller.ControllerManager;
import ois.controller.impl.ControllerManagerImpl;
import ois.model.ModelManager;
import ois.model.impl.ModelManagerImpl;

public class ApplicationManager {
	
	public static final String ALBUMNODE_NONE = "None";
	public static final String ALBUMNODE_ALL = "All";
	public static final String IMAGE_URI_PREFIX = "/ois/images/";
	
	private static ModelManager modelManager = new ModelManagerImpl();
	private static ControllerManager controllerManager = new ControllerManagerImpl(modelManager);
	
	
	
	public static ControllerManager getControllerManager(){
		return controllerManager;
	}
	
}
