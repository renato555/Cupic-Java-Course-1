package hr.fer.oprpp1.custom.collections;

/**
 * Gets thrown if the user tries to pop an empty stack.
 * @author renat
 *
 */
public class EmptyStackException extends RuntimeException{

	public EmptyStackException( String errorMessage) {
		super( errorMessage);
	}
}
