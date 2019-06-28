package anton.mobile.hangmanmvc.db;



public interface IDBFacade {

	public void setReader(IDBConnector ws);
	public String getWord(String category);
}
