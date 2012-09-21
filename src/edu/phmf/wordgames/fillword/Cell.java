package edu.phmf.wordgames.fillword;
/**
 * Ячейка поля.
 * Хранит букву и состояние
 * @author vadko_000
 *
 */
public class Cell {
	private String letter;
	private State state;
	private int position;
	
	public Cell(){
		this.state = State.UNCHECKED;
	}
	
	public Cell(int position){
		this.state = State.UNCHECKED;
		this.position = position;
	}
	
	public Cell(String letter, int position){
		this.letter = letter;
		this.state = State.UNCHECKED;
		this.position = position;
	}
	
	public void setState(State state){
		this.state = state;
	}
	
	public State getState(){
		return state;
	}
	
	public String getLetter(){
		return letter;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public void setLetter(String letter) {
		this.letter = letter;
	}

}
