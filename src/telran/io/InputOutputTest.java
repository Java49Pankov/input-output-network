
package telran.io;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Files;
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
	void displayDirContentTest() throws IOException {
		displayDirContent(Path.of(".."), 3);
	}

	private void displayDirContent(Path dirPath, int maxDepth) throws IOException {
		Path pathDir = dirPath;
		if (!Files.isDirectory(pathDir)) {
			throw new IllegalArgumentException("no directory path");
		}
		pathDir = pathDir.toAbsolutePath().normalize();
		int pathLevel = pathDir.getNameCount();
		Files.walk(pathDir, maxDepth)
		.filter(Files::isReadable)
		.forEach(dir -> {
			System.out.printf("%s<%s> - %s\n", " ".repeat((dir.getNameCount() - pathLevel) * 4), dir.getFileName(),
					Files.isDirectory(dir) ? "dir" : "files");
		});
	}
}
