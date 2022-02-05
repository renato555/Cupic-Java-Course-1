package hr.fer.zemris.java.gui.calc.components;

import javax.swing.JButton;
import java.awt.event.ActionListener;

/**
 * Button that represents a calculator digit.
 * @author renat
 *
 */
public class CalcDigitButton extends JButton{
	/**
	 * Button digit.
	 */
	private int digit;

	/**
	 * Constructor.
	 * @param digit digit to be displayed as button text.
	 * @param action action listener
	 */
	public CalcDigitButton(int digit, ActionListener action) {
		super( Integer.toString( digit));
		this.digit = digit;
		setFont( getFont().deriveFont( 30f));
		addActionListener( action);
	}
	
	public int getDigit() {
		return digit;
	}
}
