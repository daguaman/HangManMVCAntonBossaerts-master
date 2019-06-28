package anton.mobile.hangmanmvc.controller;

import anton.mobile.hangmanmvc.model.Facade;
import anton.mobile.hangmanmvc.model.IFacade;
import anton.mobile.hangmanmvc.observer.Observer;

public class MainController {
	private IFacade facade;
	public MainController(){
		facade = new Facade();
	}
	public String getSelection(){
		return facade.getSelection();
	}
	public void setWord(String word){
		facade.setWord(word);
	}
	public void checkLetter(char c){
		facade.checkLetter(c);
	}
	public String getWord(){
		return facade.getWord();
	}
	public int getLives(){
		return facade.getLives();
	}
	public void restoreGame(String word, int lives, boolean pressed[], String selection){
		facade.restoreGame(word, lives, pressed, selection);
	}
	public boolean isGameOver(){
		return facade.isGameOver();
	}
	public boolean isCorrect(){
		return facade.isCorrect();
	}
	public boolean[] getPressed() {
		return facade.getPressed();
	}
	public void registerObserver(Observer observer) {
		facade.registerObserver(observer);
		
	}
	
}
