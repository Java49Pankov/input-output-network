package telran.performance;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import telran.io.CopyFileStreams;

public class CopyFilePerformanceAppl {
	private static final int N_RUNS = 1;

	public static void main(String[] args) {
		String sourceFilePath = "bigFile";
		String destinationFilePath = "bigFileCopy";

		int[] bufferLengths = { 10_000, 100_000, 1_000_000, 100_000_000 };

		long fileSize = getSize(sourceFilePath);

		for (int bufferLength : bufferLengths) {
			runPerformanceTest(sourceFilePath, destinationFilePath, bufferLength, fileSize);
		}
	}

	private static long getSize(String filePath) {
		long res = -1;
		try {
			Path path = Paths.get(filePath);
			res = Files.size(path);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		return res;
	}

	private static void runPerformanceTest(String srcFilePath, String destFilePath, int bufferLength,
			long fileSize) {
		CopyFileStreams cfs = new CopyFileStreams(bufferLength);
		CopyPerformanceTest performanceTest = new CopyPerformanceTest("CopyTest", N_RUNS, srcFilePath,
				destFilePath, cfs);
		System.out.printf("File size: %d bytes, Buffer length: %d bytes\n", fileSize, bufferLength);
		performanceTest.run();
		System.out.println();
	}

}
