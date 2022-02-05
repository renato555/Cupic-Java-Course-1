package hr.fer.zemris.java.gui.calc.components;

import java.util.function.DoubleFunction;
import java.util.function.Function;
import java.awt.event.ActionListener;
import javax.swing.JButton;

/**
 * Button that represents a function.
 * @author renat
 */
public class CalcFunction extends JButton implements InvertName {
	/**
	 * Normal function.
	 */
	private Function<Double, Double> function;
	/**
	 * Inverse of a function.
	 */
	private Function<Double, Double> invFunction;
	/**
	 * String to be displayed when this button button applies normal function.
	 */
	private String display;
	/**
	 * String to be displayed when this button button applies an inverse of a function.
	 */
	private String invDisplay;
	
	/**
	 * Constructor.
	 * @param display string to be displayed when this button button applies a normal function
	 * @param invDisplay string to be displayed when this button button applies an inverse of a function
	 * @param function normal function
	 * @param invFunction inverse of a function
	 * @param action action listener
	 */
	public CalcFunction(String display, String invDisplay, Function<Double, Double> function, Function<Double, Double> invFunction, ActionListener action) {
		super( display);
		this.function = function;
		this.invFunction = invFunction;
		
		this.display = display;
		this.invDisplay = invDisplay;
		
		addActionListener( action);
	}
	
	/**
	 * Returns a normal or an inverse function depending on inv.
	 * @param inv state 
	 * @return normal or an inverse function
	 */
	public Function<Double, Double> getFunction( boolean inv) {
		if( !inv) {
			return function;
		}else {
			return invFunction;
		}
	}
		
	public void invertName( boolean inv) {
		if( !inv) {
			setText( display);
		}else {
			setText( invDisplay);
		}
	}
}
