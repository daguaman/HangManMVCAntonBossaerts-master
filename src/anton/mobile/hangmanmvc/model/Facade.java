package anton.mobile.hangmanmvc.model;

import anton.mobile.hangmanmvc.observer.Observer;

public class Facade implements IFacade{
	private Hangman game;
	public Facade(){
		game = new Hangman();
	}

	@Override
	public String getSelection(){
		return game.getSelection();
	}
	@Override
	public boolean isCorrect() {
		return game.isCorrect();
	}

	@Override
	public boolean isGameOver() {
		return game.isGameOver();
	}
	public String getWord(){
		return game.getWord();
	}

	@Override
	public void restoreGame(String word, int lives, boolean pressed[], String selection) {
		game.restoreGame(word, lives, pressed, selection);
	}

	@Override
	public int getLives() {
		return game.getLives();
	}
	@Override
	public boolean[] getPressed(){
		return game.getPressed();
	}
	@Override
	public void checkLetter(char c) {
		game.checkLetter(c);
	}

	@Override
	public void setWord(String word) {
		game.setWord(word);
	}


	@Override
	public void registerObserver(Observer observer) {
		game.registerObserver(observer);
		
	}

}
