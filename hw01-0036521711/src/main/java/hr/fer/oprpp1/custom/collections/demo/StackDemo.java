package hr.fer.oprpp1.custom.collections.demo;

import hr.fer.oprpp1.custom.collections.ObjectStack;

/**
 * Command-line application which accepts a single argument as input.
 * Input: expression in postfix notation.
 * Output: evaluated expression.
 * @author renat
 *
 */
public class StackDemo {

	public static void main( String[] args) {
		if( args.length != 1) throw new IllegalArgumentException( "application accepts only one argument as input");
		
		String[] input = args[0].split( "\\s+");
		ObjectStack stack = new ObjectStack();
		
		for( int i = 0; i < input.length; ++i) {
			try {
				Integer num = Integer.parseInt(input[i]);
				stack.push( num);
			}catch( NumberFormatException e) {
				if( isUnarnaOperacija( input[i])) {
					Integer first = (Integer) stack.pop();
					Integer result = unarnaOperaicija( first, input[i]);
					stack.push( result);
				}else {
					Integer second = (Integer) stack.pop();
					Integer first = (Integer) stack.pop();
					Integer result = binarnaOperacija( first, second, input[i]);
					stack.push( result);					
				}
			}
		}
		
		if( stack.size() != 1) {
			throw new IllegalArgumentException( "The expression is invalid");
		}else {
			Integer result = (Integer) stack.pop();
			System.out.println( "Expression evaluates to " + result.toString());
		}
	}
	/**
	 * Returns the result of the expression: (first operation second).
	 * Operation is a String +, -, /, *, %. 
	 * 
	 *  @throws ArithmeticException if the user tries to devide by 0
	 *  @throws IllegalArgumentException if the operation is unkonwn
	 */
	private static Integer binarnaOperacija( Integer first, Integer second, String operation) {
		if( operation.equals( "+")) {
			return first + second;
		}else if( operation.equals( "-")) {
			return first - second;
		}else if( operation.equals( "/")) {
			if( second == 0) throw new ArithmeticException( "division with 0 is not allowed");
			return first / second;
		}else if( operation.equals( "*")){
			return first * second;
		}else if( operation.equals( "%")) {
			return first % second;
		}else if( operation.equals( "pow")){
			return (int) Math.pow( first, second);
		}else{
			throw new IllegalArgumentException( "unknown operator: " + operation);
		}
	}
	
	private static Integer unarnaOperaicija( Integer first, String operation) {
		if( operation.equals( "sqr")) {
			return first*first;
		}else{
			throw new IllegalArgumentException( "unknown operator: " + operation);
		}
	}
	
	private static boolean isUnarnaOperacija( String operation) {
		return operation.equals( "sqr");
	}
}
