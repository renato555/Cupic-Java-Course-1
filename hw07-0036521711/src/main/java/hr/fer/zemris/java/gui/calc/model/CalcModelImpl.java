package hr.fer.zemris.java.gui.calc.model;

import java.util.ArrayList;
import java.util.List;
import java.util.function.DoubleBinaryOperator;

/**
 * Implements CalcModel.
 * @author renat
 */
public class CalcModelImpl implements CalcModel{

	/**
	 * True if the calculator is editable, false otherwise. 
	 */
	private boolean isEditable;
	/**
	 * Sign of a displayed number.
	 */
	private boolean isPositive;
	/**
	 * Currently displayed number.
	 */
	private String currInputString;
	/**
	 * Currently display number.
	 */
	private double currInputDouble;
	/**
	 * Active operand.
	 */
	private String frozenValue;
	/**
	 * Active operand.
	 */
	private Double activeOperand;
	/**
	 * Pending binary operation.
	 */
	private DoubleBinaryOperator pendingOperation;
	/**
	 * List of listeners.
	 * Called when displayed value is changed.
	 */
	private List< CalcValueListener> listeners;
	
	/**
	 * Constructor.
	 * Initialises all attributes.
	 */
	public CalcModelImpl() {
		isEditable = true;
		isPositive = true;
		
		currInputString = "";
		currInputDouble = 0;
		freezeValue( null);
		
		activeOperand = null;
		pendingOperation = null;
		
		listeners = new ArrayList<>();
	}
	
	@Override
	public void addCalcValueListener(CalcValueListener l) {
		listeners.add( l);
	}

	@Override
	public void removeCalcValueListener(CalcValueListener l) {
		listeners.remove( l);
	}

	private void obavijestiSve() {
		for( var l : listeners) {
			l.valueChanged( this);
		}
	}
	
	@Override
	public double getValue() {
		return isPositive ? currInputDouble : currInputDouble * -1;
	}

	@Override
	public void setValue(double value) {
		if( value < 0) {
			isPositive = false;
			value *= -1;
		}else {
			isPositive = true;
		}
		
		if( Double.isNaN( value)) {
			currInputString = "NaN";			
		}else if( Double.isInfinite(value)) {
			currInputString = "Infinity";
		}else{
			currInputString = Double.toString( value);
		}
		
		currInputDouble = value;
		freezeValue( null);
		isEditable = false;
		obavijestiSve();
	}

	@Override
	public boolean isEditable() {
		return isEditable;
	}

	@Override
	public void clear() {
		isPositive = true;
		currInputString = "";
		currInputDouble = 0;
		
		isEditable = true;
		
		obavijestiSve();
	}

	@Override
	public void clearAll() {
		clearActiveOperand();
		freezeValue( null);
		setPendingBinaryOperation( null);
		clear();
	}

	@Override
	public void swapSign() throws CalculatorInputException {
		if( !isEditable()) throw new CalculatorInputException( "model nije editabilan");
		freezeValue( null);
		
		isPositive = !isPositive;
		obavijestiSve();
	}

	@Override
	public void insertDecimalPoint() throws CalculatorInputException {
		if( !isEditable()) throw new CalculatorInputException( "model nije editabilan");
		freezeValue( null);
		
		if( currInputString.contains( ".")) {
			throw new CalculatorInputException( "vec postoji decimalna tocka");
		}else if( currInputString.isEmpty()) {
			throw new CalculatorInputException( "ne moze decimalna tocka prije prije unosa znamenki");
		}
		
		currInputString += '.';
		obavijestiSve();
	}

	@Override
	public void insertDigit(int digit) throws CalculatorInputException, IllegalArgumentException {
		if( !isEditable()) throw new CalculatorInputException( "model nije editabilan");
		if( digit < 0 || digit > 9) throw new IllegalArgumentException( "digit mora biti jednoznamenkast broj");
		freezeValue( null);
		
		if( currInputString.equals( "0")) {
			if( digit == 0) return;
			currInputString = ""; // remove leading 0's
		}
		
		String tempString = currInputString + digit;
		try {
			double tempDouble = Double.parseDouble( tempString);
			if( Double.isFinite( tempDouble)) {
				currInputDouble = tempDouble;
				currInputString = tempString;		
			}else {
				throw new CalculatorInputException( "ne moze se parsirati u konacni decimalni broj");
			}
		}catch(NumberFormatException e) {
			throw new CalculatorInputException( "ne moze se parsirati u konacni decimalni broj");
		}
		obavijestiSve();
	}

	@Override
	public String toString() {
		if( hasFrozenValue()) return frozenValue;
		
		if( currInputString.isEmpty()) {
			return isPositive ? "0" : "-0";
		}
		
		return isPositive ? currInputString : "-" + currInputString;
	}
	
	@Override
	public boolean isActiveOperandSet() {
		return activeOperand != null;
	}

	@Override
	public double getActiveOperand() throws IllegalStateException {
		if( !isActiveOperandSet()) throw new IllegalStateException( "aktivan operand nije postavljen");
		return activeOperand;
	}

	@Override
	public void setActiveOperand(double activeOperand) {
		freezeValue( Double.toString( activeOperand));
		this.activeOperand = activeOperand;
	}

	@Override
	public void clearActiveOperand() {
		freezeValue( null);
		activeOperand = null;
	}

	@Override
	public DoubleBinaryOperator getPendingBinaryOperation() {
		return pendingOperation;
	}

	@Override
	public void setPendingBinaryOperation(DoubleBinaryOperator op) {
		pendingOperation = op;
	}
	
	/**
	 * Setter for frozen value.
	 * @param value value that we want to freeze
	 */
	public void freezeValue( String value) {
		frozenValue = value;
	}
	
	/**
	 * @return true if the frozenValue is set, false otherwise 
	 */
	public boolean hasFrozenValue() {
		return frozenValue != null;
	}
}
