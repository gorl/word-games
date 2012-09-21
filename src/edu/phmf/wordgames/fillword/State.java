package edu.phmf.wordgames.fillword;

/**
 * Возможные состояния ячейки
 * @author vadko_000
 *
 */
public enum State {
	//выделена на текущий момент (пользователь держит палец)
	SELECTED,
	//не отмечена
	UNCHECKED,
	//отмечена
	CHECKED
}
