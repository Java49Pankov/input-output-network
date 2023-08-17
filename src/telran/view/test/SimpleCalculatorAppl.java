package telran.view.test;

import telran.view.*;

public class SimpleCalculatorAppl {

	public static void main(String[] args) {
		InputOutput io = new ConsoleInputOutput();
		Menu menu = new Menu("Calculator", NumberOperations.getItems(), DateOperations.getDateItems(), Item.ofExit());

		menu.perform(io);
	}
}
