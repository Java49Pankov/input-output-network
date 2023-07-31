package telran.io;

import java.io.*;

public class CopyFileStreams implements CopyFile {
	private int bufferLength;

	public CopyFileStreams(int bufferLength) {
		super();
		this.bufferLength = bufferLength;
	}

	@Override
	public void copy(String pathToSource, String pathToDestination) {
		try (InputStream input = new FileInputStream(pathToSource);
				OutputStream output = new FileOutputStream(pathToDestination)) {
			byte[] buf = new byte[bufferLength];
			
			int inputLength = input.read(buf);
			
			while (inputLength > 0) {
				output.write(buf, 0, inputLength);
				inputLength = input.read(buf);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

}
