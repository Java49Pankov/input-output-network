package telran.view.test;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.function.BinaryOperator;
import java.util.function.UnaryOperator;

import telran.view.InputOutput;
import telran.view.Item;
import telran.view.Menu;

public class DateOperations {
	static Item getDateItems() {
		return new Menu("Date operations",
				new Item[] {
						Item.of("Date after a given number of days", io -> calculateDate(io,
								date -> date.plusDays(io.readInt("Enter the number of days",
										"Invalid input, must be integer number")))),
						Item.of("Date before a given number of days",
								io -> calculateDate(io,
										date -> date.minusDays(io.readInt("Enter the number of days",
												"Invalid input, must be integer number")))),
						Item.of("Days between two dates", io -> calculateDaysBetween(io)), Item.ofExit() });
	}

	static void calculateDate(InputOutput io, UnaryOperator<LocalDate> operator) {
		LocalDate date = io.readDate("Enter a date in ISO format", "Invalid date format");
		io.writeLine(operator.apply(date));
	}

	static void calculateDaysBetween(InputOutput io) {
		LocalDate firstDate = io.readDate("Enter the first date in ISO format", "Invalid date format");
		LocalDate secondDate = io.readDate("Enter the second date in ISO format", "Invalid date format");
		long daysBetween = ChronoUnit.DAYS.between(firstDate, secondDate);
		io.writeLine("Days between the two dates: " + daysBetween);
	}
}
