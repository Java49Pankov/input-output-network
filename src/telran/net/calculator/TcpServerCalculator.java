package telran.net.calculator;

import java.net.*;
import java.util.function.BinaryOperator;
import java.io.*;
import telran.view.*;

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
			System.out.println("Client close abnormally connection");
		}
	}

	private static String getResponse(String line) {
		String response = "Wrong request structure";
		String[] tokens = line.split("#");
		if (tokens.length == 3) {
			String requestType = tokens[0];
			double firstOperand = Double.parseDouble(tokens[1]);
			double secondOperand = Double.parseDouble(tokens[2]);
			response = calculate(requestType, firstOperand, secondOperand);
		}
		return response;
	}

	private static String calculate(String requestType, double firstOperand, double secondOperand) {
		String response = switch (requestType) {
		case "add" -> String.valueOf(firstOperand + secondOperand);
		case "subtract" -> String.valueOf(firstOperand - secondOperand);
		case "multiply" -> String.valueOf(firstOperand * secondOperand);
		case "divide" ->
			secondOperand != 0 ? String.valueOf(firstOperand / secondOperand) : "Division by zero is not allowed";
		default -> "wrong request type";
		};
		return response;
	}

}
