package anton.mobile.hangmanmvc.controller;


import anton.mobile.hangmanmvc.db.DBFacade;
import anton.mobile.hangmanmvc.db.IDBFacade;
import anton.mobile.hangmanmvc.db.OfflineWordSelector;
import anton.mobile.hangmanmvc.db.OnlineWordSelector;

public class LoaderController {
	private IDBFacade dbfacade;
	public LoaderController(boolean isConnected){
		dbfacade = new DBFacade();
		if(isConnected){
			dbfacade.setReader(new OnlineWordSelector());
		} else {
			dbfacade.setReader(new OfflineWordSelector());
		}
	}
	
	public String getWord(String category){
		return dbfacade.getWord(category);
	}

}
