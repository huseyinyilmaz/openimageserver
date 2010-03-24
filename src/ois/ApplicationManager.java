package ois;

import ois.controller.ControllerManager;
import ois.controller.impl.ControllerManagerImpl;
import ois.model.ModelManager;
import ois.model.impl.ModelManagerImpl;

public class ApplicationManager {
	
	public static final String IMAGE_URI_PREFIX = "img/";
	private static ModelManager modelManager = new ModelManagerImpl();
	private static ControllerManager controllerManager = new ControllerManagerImpl(modelManager);
	
	
	
	public static ControllerManager getControllerManager(){
		return controllerManager;
	}
	
	public static ModelManager getModelManager(){
		return modelManager;
	}
}
