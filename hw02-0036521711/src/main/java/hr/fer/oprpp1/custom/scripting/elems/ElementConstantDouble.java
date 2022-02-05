package hr.fer.oprpp1.custom.scripting.elems;

/**
 * Representation of a constant double.
 * @author renat
 *
 */
public class ElementConstantDouble extends Element{
	private double value;

	public ElementConstantDouble( double value) {
		this.value = value;
	}
	
	@Override
	public String asText() {
		return Double.toString( value);
	}
	
	public double getValue() {
		return value;
	}

	@Override
	public String toString() {
		return Double.toString( value);
	}
	
	
}
