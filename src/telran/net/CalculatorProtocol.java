package telran.net;

public class CalculatorProtocol implements ApplProtocol {

	@Override
	public Response getResponse(Request request) {
		System.out.println(request);
		String responseCalc = getResponseCalculate((Request) request.requestData());
		Response response = new Response(ResponseCode.OK, responseCalc);
		return (Response) response;
	}

	private String getResponseCalculate(Request request) {
		String responseData = "Wrong request structure, usage: <operation type>#<operand 1>#<operand 2>";
		try {
			double[] operand = (double[]) request.requestData();
			responseData = switch (request.requestType()) {
			case "add" -> Double.toString(operand[0] + operand[1]);
			case "minus" -> Double.toString(operand[0] - operand[1]);
			case "multiply" -> Double.toString(operand[0] * operand[1]);
			case "divide" ->
				operand[1] != 0 ? String.valueOf(operand[0] / operand[1]) : "Division by zero is not allowed";
			default -> "wrong request type";
			};
		} catch (Exception e) {
			responseData = e.getMessage();
		}
		return responseData;
	}
}
