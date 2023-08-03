package telran.io;

import java.io.*;

public class TextCommentsSeparation {

	public static void main(String[] args) {
		if (args.length < 3) {
			System.out.println("Usage: must be three arguments (source, textOutput, commentsOutput)");
		} else {
			try (BufferedReader reader = getReader(args[0]);
					PrintWriter textOutput = new PrintWriter(args[1]);
					PrintWriter commentsOutput = new PrintWriter(args[2])) {
				inputOutput(reader, textOutput, commentsOutput);
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
		}
	}

	private static BufferedReader getReader(String filePath) throws FileNotFoundException {
		return new BufferedReader(new FileReader(filePath));
	}

	private static void inputOutput(BufferedReader reader, PrintWriter textOutput, PrintWriter commentsOutput)
			throws IOException {
		String line = reader.readLine();
		while (line != null) {
			if (line.trim().startsWith("//")) {
				commentsOutput.println(line);
			} else {
				textOutput.println(line);
			}
			line = reader.readLine();
		}
	}

}
