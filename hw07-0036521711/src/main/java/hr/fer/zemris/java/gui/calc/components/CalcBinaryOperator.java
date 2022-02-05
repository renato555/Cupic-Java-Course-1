package hr.fer.zemris.java.gui.calc.components;

import java.util.function.DoubleBinaryOperator;
import java.awt.event.ActionListener;
import javax.swing.JButton;

/**
 * Button that represents a binary operator.
 * @author renat
 *
 */
public class CalcBinaryOperator extends JButton{
	/**
	 * Button text.
	 */
	protected String display;
	/**
	 * Operation that will be performed after this button is pressed.
	 */
	private DoubleBinaryOperator operation;

	/**
	 * Constructor.
	 * Initialises all attributes.
	 * @param display string to be set as button text. 
	 * @param operation operation that will be performed after this button is pressed.
	 * @param action action listener
	 */
	public CalcBinaryOperator(String display, DoubleBinaryOperator operation, ActionListener action) {
		super( display);
		this.display = display;
		this.operation = operation;
		
		addActionListener( action);
	}
	
	public DoubleBinaryOperator getOperation() {
		return operation;
	}
}
