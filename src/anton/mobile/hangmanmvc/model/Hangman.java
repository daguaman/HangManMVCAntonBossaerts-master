package anton.mobile.hangmanmvc.model;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import anton.mobile.hangmanmvc.observer.Observer;
import anton.mobile.hangmanmvc.observer.Subject;

public class Hangman implements Subject{
	private Set<Observer> observers = new HashSet<Observer>();
	private String word;
	private int lives;
	private String selection = "";
	private boolean pressed[] = new boolean[26];
	
	public Hangman(){
		this.lives=6;
		for(int i = 0; i < 0; i++){
			pressed[i] = false;
		}
	}
	public String getSelection(){
		return this.selection;
	}
	public String getWord(){
		return this.word;
	}
	public void setWord(String word) {
		this.word = word;
		if(!word.equals("")){
			for(int i = 0; i < word.length(); i++){
				selection = selection + "_";
			}
		}
	}

	public void checkLetter(char c) {
		boolean found = false;
		pressed[c-'a'] = true;
		for(int i = 0; i < word.length(); i++){
			if(word.charAt(i) == c){
				found = true;
				String tempString = "";
				tempString = ""+selection.subSequence(0, i)+c; //lazy cast
				if(i!=word.length()){
				tempString = tempString + selection.substring(i+1, selection.length());
				}
				selection = tempString;
			}
			
		}
		if(!found){
			lives--;
		}
		notifyObservers();
		
	}

	public int getLives() {
		return this.lives;
	}

	public void restoreGame(String word, int lives, boolean pressed[], String selection) {
		this.word = word;
		this.lives = lives;
		this.pressed = pressed;
		this.selection = selection;
	}

	public boolean isGameOver() {
		return (lives==0);
	}

	public boolean isCorrect() {
		if(selection.equals(word)){
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void registerObserver(Observer observer) {
		observers.add(observer);
		
	}

	@Override
	public void removeObserver(Observer observer) {
		Iterator<Observer> iterator = observers.iterator();
		while (iterator.hasNext()) {
			Observer o = iterator.next();
			if (o.equals(observer)) {
				iterator.remove();
			}
		}
		
	}

	@Override
	public void notifyObservers() {
		for (Observer o : observers) {
			o.update();
		}
	}
	public boolean[] getPressed() {
		return pressed;
	}

}
