package hr.fer.oprpp1.custom.scripting.elems;

/**
 * Representation of a constant integer
 * @author renat
 *
 */
public class ElementConstantInteger extends Element{
	private int value;

	public ElementConstantInteger( int value) {
		this.value = value;
	}
	
	@Override
	public String asText() {
		return Integer.toString( value);
	}
	
	public int getValue() {
		return value;
	}

	@Override
	public String toString() {
		return Integer.toString( value);
	}
	
	
}
