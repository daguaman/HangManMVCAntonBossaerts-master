package anton.mobile.hangmanmvc.db;


public class DBFacade implements IDBFacade {
	IDBConnector ws;
	public DBFacade(){
	
	}
	@Override
	public void setReader(IDBConnector ws) {
		this.ws = ws;
		
	}

	@Override
	public String getWord(String category) {
		return ws.getWord(category);
	}



}
