package hr.fer.oprpp1.custom.scripting.elems;

/**
 * Representation of a function.
 * @author renat
 *
 */
public class ElementFunction extends Element{
	private String name;

	public ElementFunction( String name) {
		if( name == null) throw new NullPointerException( "name can not be null");
		
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
		return "@" + name;
	}
	
	
}
