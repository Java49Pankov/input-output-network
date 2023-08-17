package telran.view.test;

import java.util.function.BinaryOperator;

import telran.view.*;

public class NumberOperations {
	static Item getItems() {
		return new Menu("Number Operations",
				new Item[] { Item.of("Add number", io -> calculate(io, (a, b) -> a + b)),
						Item.of("Subtract numbers", io -> calculate(io, (a, b) -> a - b)),
						Item.of("Multiply numbers", io -> calculate(io, (a, b) -> a * b)),
						Item.of("Divide numbers", io -> calculate(io, (a, b) -> a / b)), Item.ofExit() });
	}

	 static void calculate(InputOutput io, BinaryOperator<Double> operator) {
		double first = io.readDouble("Enter first number", "Must be any number");
		double second = io.readDouble("Enter second number", "Must be any number");
		io.writeLine(operator.apply(first, second));
	}
}
