package telran.net.calculator;

import java.net.*;
import java.io.*;

public class TcpServerCalculator {
	static final int PORT = 4000;

	public static void main(String[] args) throws Exception {

		ServerSocket serverSocket = new ServerSocket(PORT);
		System.out.println("Server is listening to port" + PORT);
		while (true) {
			Socket socket = serverSocket.accept();
			clientRun(socket);
		}
	}

	private static void clientRun(Socket socket) {
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				PrintStream writer = new PrintStream(socket.getOutputStream())) {
			while (true) {
				String line = reader.readLine();
				if (line == null) {
					System.out.println("Client close normally connection");
					break;
				}
				String response = getResponse(line);
				writer.println(response);
			}
		} catch (Exception e) {
			System.out.println("Clien close abnormally connection");
		}
		;

	}

	private static String getResponse(String line) {
		// TODO Auto-generated method stub
		return null;
	}

}
