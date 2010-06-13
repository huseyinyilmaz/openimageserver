package ois.controller;

import java.util.Comparator;

import ois.view.DataBean;

public class RevisionComparetor implements Comparator<DataBean> {
	@Override
	public int compare(DataBean o1, DataBean o2) {
		int result = 0;
		if(o1.isOriginal()||o1.isThumbnail()||o2.isOriginal()||o2.isThumbnail())
			//at least one of o1 or o2 is original or thumbnail
			if( (o1.isOriginal()||o1.isThumbnail()) && (o2.isOriginal()||o2.isThumbnail()) )
				//both o1 and o2 is thumbnail or original
				if(o1.isOriginal())
					//o1 is original 
					result=-1;
				else 
					//o2 is original
					result = 1;
			else
				//only one of o1 or o2 is original or thumbnail
				if(o1.isOriginal()||o1.isThumbnail())
					//o1 is original or thumbnail
					return -1;
				else
					//o2 is original or thumbnail
					return 1;
		else{
			//o1 or o2 is not original or thumbnail
			result = o1.getWidth() - o2.getWidth();
			if(result==0){
				result = o1.getHeight()-o2.getHeight();
				if(result==0)
					if(o2.isEnhanced())
						result = -1;
					else
						result = 1;
			}
		}
		return result;
	}
}