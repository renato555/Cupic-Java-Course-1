package hr.fer.oprpp1.custom.scripting.elems;

/**
 * Representation of an operator.
 * @author renat
 *
 */
public class ElementOperator extends Element{
	private String symbol;

	public ElementOperator( String symbol) {
		if( symbol == null) throw new NullPointerException( "symbol can not be null");
		this.symbol = symbol;
	}
	
	@Override
	public String asText() {
		return toString();
	}
	
	public String getSymbol() {
		return symbol;
	}

	@Override
	public String toString() {
		return symbol;
	}
	
	
}
