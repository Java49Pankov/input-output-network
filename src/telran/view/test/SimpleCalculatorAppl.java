package telran.view.test;

import telran.view.*;

public class SimpleCalculatorAppl {

	public static void main(String[] args) {
		InputOutput io = new ConsoleInputOutput();
		Menu menu = new Menu("Calculator", getItems());
		menu.perform(io);
	}

	static double[] getToNumbers(InputOutput io) {
		double first = io.readDouble("Enter first number", "Must be any number");
		double second = io.readDouble("Enter second number", "Must be any number");
		return new double[] { first, second };
	}

	static Item[] getItems() {
		Item[] items = { Item.of("Add number", SimpleCalculatorAppl::add),
				Item.of("Subtract numbers", SimpleCalculatorAppl::subtract),
				Item.of("Multiply numbers", SimpleCalculatorAppl::multiply),
				Item.of("Divide numbers", SimpleCalculatorAppl::divide), Item.ofExit() };

		return items;
	}

	static void add(InputOutput io) {
		double[] numbers = getToNumbers(io);
		io.writeLine(numbers[0] + numbers[1]);
	}

	static void subtract(InputOutput io) {
		double[] numbers = getToNumbers(io);
		io.writeLine(numbers[0] - numbers[1]);
	}

	static void multiply(InputOutput io) {
		double[] numbers = getToNumbers(io);
		io.writeLine(numbers[0] * numbers[1]);
	}

	static void divide(InputOutput io) {
		double[] numbers = getToNumbers(io);
		io.writeLine(numbers[0] / numbers[1]);
	}
}
