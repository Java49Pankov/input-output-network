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
			System.out.println("Clien close abnormally connection");
		}
	}

	private static String getResponse(String line) {
		String response = "Wrong request structure";
		String[] tokens = line.split("#");
		if (tokens.length == 3) {
			String requestType = tokens[0];
			double firstOperand = Double.parseDouble(tokens[1]);
			double secondOperand = Double.parseDouble(tokens[2]);
			switch (requestType) {
			case "add":
				response = String.valueOf(firstOperand + secondOperand);
				break;
			case "subtract":
				response = String.valueOf(firstOperand - secondOperand);
				break;
			case "multiply":
				response = String.valueOf(firstOperand * secondOperand);
				break;
			case "divide":
				if (secondOperand != 0) {
					response = String.valueOf(firstOperand / secondOperand);
				} else {
					response = "Division by zero is not allowed";
				}
				break;
			default:
				response = "wrong request type";
				break;
			}
		}
		return response;
	}

}
