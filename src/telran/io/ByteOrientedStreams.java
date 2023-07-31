package telran.io;

import static org.junit.jupiter.api.Assertions.*;

import java.io.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ByteOrientedStreams {

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void smallFileOutputStream() throws Exception {
		try (OutputStream output = new FileOutputStream("smallFile.txt")) {
			byte[] array = "Hello world".getBytes();
			output.write(array);
		}
	}

	@Test
	void bigFileOutputStream() throws Exception {
		try (OutputStream output = new FileOutputStream("bigFile")) {
			for (int j = 0; j < 2; j++) {
				StringBuilder builder = new StringBuilder(100_000_000);
				for (int i = 0; i < 50_000_000; i++) {
					builder.append("Hello Worl");
				}
				output.write(builder.toString().getBytes());
			}
		}
	}

	@Test
	void bigFileInputStream() throws Exception {
		try (InputStream input = new FileInputStream("bigFile")) {
			byte[] buf = new byte[1_000_000];
			long totalSize = 0;
			int length = 0;
			while ((length = input.read(buf)) > 0) {
				totalSize += length;
			}
			assertEquals(1_000_000_000l, totalSize);
		}
	}
}
