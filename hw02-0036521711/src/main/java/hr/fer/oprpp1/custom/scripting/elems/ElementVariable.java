package hr.fer.oprpp1.custom.scripting.elems;

/**
 * Representation of a variable.
 * @author renat
 *
 */
public class ElementVariable extends Element{
	private String name;
	
	public ElementVariable( String name) {
		if( name == null) throw new NullPointerException( "name ne moze biti null");
		this.name = name;
	}
	
	@Override
	public String asText() {
		return toString();
	}
	
	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return name;
	}
	
	
}
