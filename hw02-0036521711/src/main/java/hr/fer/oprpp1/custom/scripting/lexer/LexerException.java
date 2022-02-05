package hr.fer.oprpp1.custom.scripting.lexer;

/**
 * Thrown when lexer encounters a problem.
 * @author renat
 *
 */
public class LexerException extends RuntimeException {
	
	/**
	 * Default contructor.
	 * @param message error message
	 */
	public LexerException( String message) {
		super( message);
	}
}
