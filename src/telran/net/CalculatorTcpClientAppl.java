package telran.net;

import java.util.*;

import telran.view.*;

public class CalculatorTcpClientAppl {
	static final String HOST = "localhost";
	static final int PORT = 5000;

	public static void main(String[] args) throws Exception {
		try (TcpHandler tcpHandler = new TcpHandler("localhost", PORT)) {
			InputOutput io = new ConsoleInputOutput();
			Menu menu = new Menu("Calculator Application", Item.of("send request", io1 -> {
				HashSet<String> requests = new HashSet<>(Arrays.asList("add", "minus", "multiply", "divide"));
				String requestType = io1.readString("Enter operation type " + requests, "Wrong operation", requests);
				double operand1 = io1.readDouble("Enter first number", "Wrong number");
				double operand2 = io1.readDouble("Enter second number", "Wrong number");

				double[] requestData = { operand1, operand2 };

				Request request = new Request(requestType, requestData);

				String response = tcpHandler.send(requestType, request);

				io1.writeLine(response);
			}), Item.ofExit());
			menu.perform(io);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}