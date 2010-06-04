package ois.cron;

import java.io.IOException;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ois.model.AlbumFile;
import ois.model.ImageData;
import ois.model.ImageFile;
import ois.model.PMF;

public class DeleteTempFiles extends HttpServlet {
	
	private static final long serialVersionUID = 6444058513192509942L;

	@SuppressWarnings("unchecked")
	public void doGet(HttpServletRequest req, HttpServletResponse resp)throws IOException{
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try{
			Query query = pm.newQuery(ImageData.class);
			query.setFilter("isGlobal == false");
			List<ImageData> imageDataList = (List<ImageData>) query.execute();
			pm.deletePersistentAll(imageDataList);

			query = pm.newQuery(ImageFile.class);
			query.setFilter("isGlobal == false");
			List<ImageFile> imageFileList = (List<ImageFile>) query.execute();
			pm.deletePersistentAll(imageFileList);

			query = pm.newQuery(AlbumFile.class);
			query.setFilter("owner != null");
			List<AlbumFile> albumFileList = (List<AlbumFile>) query.execute();
			pm.deletePersistentAll(albumFileList);
		}finally{
			pm.close();
		}	
	}
	
}
