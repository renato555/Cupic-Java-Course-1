package hr.fer.zemris.java.gui.layouts;

/**
 * Thrown when there is an exception in CalcLayout.
 * @author renat
 *
 */
public class CalcLayoutException extends RuntimeException{	
	/**
	 *  Default serial version.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor.
	 * @param msg error msg
	 */
	public CalcLayoutException( String msg) {
		super( msg);
	}
}
