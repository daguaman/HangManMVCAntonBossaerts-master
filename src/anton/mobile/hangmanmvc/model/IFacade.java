package anton.mobile.hangmanmvc.model;

import anton.mobile.hangmanmvc.observer.Observer;

public interface IFacade {

	boolean isCorrect();

	boolean isGameOver();

	void restoreGame(String word, int lives, boolean pressed[], String selection);

	int getLives();

	void checkLetter(char c);

	void setWord(String word);

	String getWord();
	
	String getSelection();

	boolean[] getPressed();

	void registerObserver(Observer observer);

}
