package telran.view.console.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.*;
import telran.view.console.*;

class ConsoleInputOutputTest {
	ConsoleInputOutput consoleInputOutput;

	@BeforeEach
	void setUp() throws Exception {
		consoleInputOutput = new ConsoleInputOutput();
	}

	@Disabled
	@Test
	void testReadInt() {
		int res = consoleInputOutput.readInt("Enter number", "Number must be Integer");
//		assertEquals(10, res);
	}

	@Disabled
	@Test
	void testReadIntMinMax() {
		consoleInputOutput.readInt("Enter number", "Number must be Integer", 5, 10);
	}

	@Disabled
	@Test
	void testReadLong() {
		consoleInputOutput.readLong("Enter number", "Number must be Integer");
	}

	@Disabled
	@Test
	void testReadLongMinMax() {
		consoleInputOutput.readLong("Enter number", "Number must be Integer", 1000, 2000);

	}

	@Disabled
	@Test
	void testReadStringPredicate() {
//		String[] department = { "dep1", "dep2", "dep3", "dep4" };
//		for (String dep : department) {
//			final String actualDep = dep;
		consoleInputOutput.readStringPredicate("Enter department", "Department must be department",
				pred -> pred.contains("department"/* actualDep */));
//		}
	}

	@Disabled
	@Test
	void testReadStringOptions() {
		Set<String> departmentOptions = new HashSet<>(Arrays.asList("dep1", "dep2", "dep3", "dep4"));

		for (String dep : departmentOptions) {
			consoleInputOutput.readString("Enter department", "Department must be one of: " + departmentOptions,
					departmentOptions);
		}
	}

	@Disabled
	@Test
	void testReadDate() {
		consoleInputOutput.readDate("Enter date in ISO format", "Date format must be ISO: 2007-12-03");
	}
	@Disabled
	@Test
	void testReadDateFromTO() {
		LocalDate fromDate = LocalDate.of(2022, 1, 1);
		LocalDate toDate = LocalDate.of(2022, 12, 31);
		consoleInputOutput.readDate("Enter date in ISO format", "Date format must be ISO: 2007-12-03", fromDate,
				toDate);
	}

	
	@Test
	void testReadDouble() {
		consoleInputOutput.readDouble("enter a fractional number", "Number must be fractional number");
	}

}
