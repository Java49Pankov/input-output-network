package telran.net.calculator;

import java.io.*;
import java.net.*;
import java.util.*;
import telran.view.*;

public class TcpClientCalculator {
	static final String HOST = "localhost";
	static final int PORT = 4000;

	public static void main(String[] args) throws Exception {

		try (Socket socket = new Socket(HOST, PORT);
				PrintStream writer = new PrintStream(socket.getOutputStream());
				BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
			InputOutput io = new ConsoleInputOutput();
			Menu menu = new Menu("TCP client calculator", Item.of("Send request", io1 -> {
				HashSet<String> requests = new HashSet<>(Arrays.asList("add", "subtract", "multiply", "divide"));
				String requestType = io1.readString("Enter request type" + requests, HOST, requests);
				double firstOperand = io1.readDouble("Enter first operand", "Must be any number");
				double secondOperand = io1.readDouble("Enter second operand", "Must be any number");
				writer.println(String.format("%s#%s#%s", requestType, firstOperand, secondOperand));
				try {
					String response = reader.readLine();
					io1.writeLine(response);
				} catch (IOException e) {
					throw new RuntimeException(e.toString());
				}
			}), Item.ofExit());
			menu.perform(io);
		}
	}
}