package hr.fer.zemris.java.hw06.shell;

/**
 * Represents shell exception.
 * Thrown when communication with the user fails.
 * @author renat
 */
public class ShellIOException extends RuntimeException {

	/**
	 * @param message error message
	 */
	public ShellIOException(String message) {
		super(message);
	}
}
