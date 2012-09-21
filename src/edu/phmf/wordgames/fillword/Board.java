package edu.phmf.wordgames.fillword;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import edu.phmf.wordgames.dictionary.Dictionary;

/**
 * ����� � ������� �����. �������� ��� ������ ����
 * ���� ����, ������� ������ ������������ ��������
 * @author vadko_000
 *
 */
public class Board {
	//������� ����
	public static final int COLUMNS = 5;
	public static final int ROWS= 5;
	//�������
	private static Board inst;
	//���� ����
	private Cell fild[][];
	//������ ���������� �����
	private List<Cell> selectedCells;
	//������ ����� ������� � ��������� ����������
	private List<Cell> nearCells;
	//������ ���� �� ���
	private Random rand;
	//�������
	private static final String rusLetters[] = {"�","�","�","�","�","�","�","�","�","�","�","�","�","�","�","�","�","�","�","�","�","�","�","�","�","�","�","�","�","�","�","�","�","�","�","�","�","�","�","�","�","�","�","�","�","�","�","�","�","�","�"};
	private Board(){
		fild = new Cell[COLUMNS][ROWS];
		for (int i = 0; i < COLUMNS; ++i){
			for (int j = 0; j < ROWS; ++j)
				fild[i][j] = new Cell(i * COLUMNS + j);
		}
		//���������� arrayList �.�. �������� ����� ����� ������� 15 ����, � �� ������� ������� LinkedList
		selectedCells =  new ArrayList<Cell>(15);
		//����� ���� ������ 8 ������� �����
		nearCells = new ArrayList<Cell>(8);
		rand = new Random();

	}
	
	public static Board getInstance(){
		if(inst == null)
			inst = new Board();
		return inst;
	}
	/**
	 * ������������ ��� �������� � ��������
	 * @param position
	 */
	public void evaluateCell(int position){
		System.out.println("evaluate position " + position);
		Cell cell = getCellByPosition(position);
		if (cell.getState() == State.UNCHECKED){
			System.out.println("unchecked");
			cell.setState(State.CHECKED);
			//���� ������� ���������� �����, �� �������� �����
			if (!canAddToSelected(cell)){
				System.out.println("can't add to selected");
				clearSelected();
			}
			selectedCells.add(cell);
		}
		else{
			System.out.println("checked");
			boolean isFound = false;
			//������� ��������� �� ���� ����� ����� ����������
			for (Cell selectedCell : selectedCells){
				if (isFound){
					selectedCell.setState(State.UNCHECKED);
					System.out.println("deselect " + selectedCell.getPosition());
				}
				if (selectedCell.equals(cell)){
					System.out.println("FOUND");
					isFound = true;
				}
			}
			//�������� ������ ����������
			System.out.print("list before cut: ");
			for (Cell c : selectedCells)
				System.out.print(c.getPosition() + " ");
			
			selectedCells = selectedCells.subList(0, selectedCells.lastIndexOf(cell) + 1);
			System.out.println("\n after cut : " + selectedCells.size());
			for (Cell c : selectedCells)
				System.out.print(c.getPosition() + " ");
			System.out.println(' ');
		}
		
		refreshNearCells(cell);
		System.out.print("near cells after refreshing: ");
		for (Cell c : nearCells)
			System.out.print(c.getPosition() + " ");
		System.out.println(" ");
		System.out.print("selected cells after refreshing: ");
		for (Cell c : selectedCells)
			System.out.print(c.getPosition() + " ");
		System.out.println(" ");
	}
	
	public void getnerateField(){
		Dictionary dic = Dictionary.getInstance();
		//������ �������
		for (int i = 10; i > 9; i -= 1){
			String randomWord = dic.getRandomWord(i);
			if (randomWord == null){
				System.out.println("null random " + i);
			}
			addWord(randomWord);
		}
		System.out.println("adding random letters");
		//addRandomLetters();
		
	}

	private void addRandomLetters() {
		for(int i = 0; i < COLUMNS; ++i){
			for (int j = 0; j < ROWS; ++j){
				if (fild[i][j].getLetter() == null)
					fild[i][j].setLetter(rusLetters[rand.nextInt(rusLetters.length - 1)]);
			}
		}
		
	}

	private void addWord(String word) {
		//������� ���� ��� ���������
		int shift = 0;
		//��������� �����������
		int dir;
		//������� �����
		String letter;
		// ������� �������
		int pos = rand.nextInt(COLUMNS * ROWS);
		pos = getValidDir(pos, 0, "");
		
		System.out.println("word: " + word);
		
		for (int j = 0; j < word.length(); ++j){
			if (pos == -1)
				return;
			letter = word.substring(shift, shift + 1);
			getCellByPosition(pos).setLetter(letter);
			//��������� �����������
			dir = Math.abs(rand.nextInt());
			System.out.println("letter is " + letter + " and dir " + dir);
			pos = getValidDir(pos, dir, letter);	
			shift++;
		}
	}

	/**
	 * ����� ��������� �������� ������� ���������� �����
	 * @return
	 */
	public String getCurrentWord(){
		StringBuilder word = new StringBuilder(selectedCells.size());
		for (Cell cell: selectedCells){
			word.append(cell.getLetter());
		}
		return word.toString();
	}


	public Cell getCellByPosition(int position) {
		int i = position / COLUMNS;
		int j = position % COLUMNS;
		return fild[i][j];
	}
	
	/**
	 * ��������� ������� ��������� ������ ������������ � ��� ����������
	 * @param cell
	 * @return
	 */
	private boolean canAddToSelected(Cell cell){
		if (nearCells.contains(cell) || nearCells.isEmpty())
			return true;
		else 
			return false;
	}
	
	/**
	 *������� ������ ���������� ����� � ��������� ��������� 
	 */
	private void clearSelected() {
		for (Cell cell : selectedCells)
			cell.setState(State.UNCHECKED);
		selectedCells.clear();
	}
	
	/**
	 * ��������� nearCells �������� �������� � cell
	 * @param cell
	 */
	private void refreshNearCells(Cell cell) {
		nearCells.clear();
		int position = cell.getPosition();
		boolean allowTop = (position / COLUMNS != 0);
		boolean allowDown = (position / COLUMNS != ROWS - 1);
		boolean allowLeft = (position % COLUMNS != 0);
		boolean allowRight = (position % COLUMNS != ROWS - 1);
		
		if (allowTop){
			//������ ��� ����������
			nearCells.add(getCellByPosition(position - COLUMNS));
			//������-�����
			if(allowLeft)
				nearCells.add(getCellByPosition(position - COLUMNS - 1));
			//������-������
			if (allowRight)
				nearCells.add(getCellByPosition(position - COLUMNS + 1));
		}
		if (allowDown){
			//������ ��� ����������
			nearCells.add(getCellByPosition(position + COLUMNS));
			//�����-�����
			if(allowLeft)
				nearCells.add(getCellByPosition(position + COLUMNS - 1));
			//�����-������
			if (allowRight)
				nearCells.add(getCellByPosition(position + COLUMNS + 1));
		}
		//�����
		if (allowLeft)
			nearCells.add(getCellByPosition(position - 1));
		//������
		if (allowRight)
			nearCells.add(getCellByPosition(position + 1));		
	}
	
	private int getValidDir(int pos, int randDir, String letter){
		int fails = 0;
		Cell cell = getCellByPosition(pos);
		//��������� nearCells ��������� ��������
		refreshNearCells(cell);
		while (fails < 8){
			Cell possible = nearCells.get(randDir % nearCells.size());
			if (possible.getLetter() == null || possible.getLetter().equals(letter))
				return possible.getPosition();
			fails++;
			randDir++;
		}
		return -1;
		
	}

}
