package hr.fer.oprpp1.custom.scripting.parser;

/**
 * Thrown when parser encounters a problem.
 * @author renat
 *
 */
public class SmartScriptParserException extends RuntimeException {
	
	/**
	 * Default contructor.
	 * @param message error message
	 */
	public SmartScriptParserException( String message) {
		super( message);
	}
}
