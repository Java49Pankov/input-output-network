
package telran.io;

import static org.junit.jupiter.api.Assertions.*;

import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class InputOutputTest {

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void pathTest() {
		Path pathParent = Path.of(".");
		System.out.println(pathParent.toAbsolutePath().normalize());
	}

	@Test
	void displayDirContentTest() {
		// TODO
		displayDirContent(Path.of("../.."), 3);
	}

	private void displayDirContent(Path dirPath, int maxDepth) {
		// TODO Auto-generated method stub
		// Display content directory by using method walk of the class Files
	}

}
