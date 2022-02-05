package hr.fer.zemris.java.gui.calc.components;

import java.awt.event.ActionListener;
import java.util.function.BiFunction;
import java.util.function.DoubleBinaryOperator;
import java.util.function.Function;

import javax.swing.JButton;
import javax.swing.JCheckBox;

/**
 * Button that represents a binary operator and its inverse.
 * Proxy of a CalcBinarOperator.
 * @author renat
 *
 */
public class CalcBinaryOperatorInv extends CalcBinaryOperator implements InvertName {
	/**
	 * String to be displayed when the operator is inverted.
	 */
	private String invDisplay;
	/**
	 * Inverse operator.
	 */
	private DoubleBinaryOperator invOperation;
	/**
	 * Determines if the button should return an inverse or a normal operator.
	 */
	private JCheckBox invButton;
	
	/**
	 * Constructor. 
	 * @param display string to be display as button text when button performs normal operation
	 * @param operation normal operation
	 * @param invDisplay string to be display as button text when button performs inverse operation
	 * @param invOperation inverse operation
	 * @param invButton determines which operation this button should apply
	 * @param action action listener
	 */
	public CalcBinaryOperatorInv(String display, DoubleBinaryOperator operation,
			String invDisplay, DoubleBinaryOperator invOperation, JCheckBox invButton, ActionListener action) {
		super(display, operation, action);
		this.invDisplay = invDisplay;
		this.invOperation = invOperation;
		this.invButton = invButton;
	}

	/**
	 * Returns normal or inverse operation depending on invButton state. 
	 */
	@Override
	public DoubleBinaryOperator getOperation() {
		if( invButton.isSelected()) {
			return invOperation;
		}else {
			return super.getOperation();			
		}
	}

	@Override
	public void invertName(boolean inv) {
		if (!inv) {
			setText(display);
		} else {
			setText(invDisplay);
		}
	}
}
