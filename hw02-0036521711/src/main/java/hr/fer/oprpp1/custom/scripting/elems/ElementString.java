package hr.fer.oprpp1.custom.scripting.elems;

/**
 * Representation of a string.
 * @author renat
 *
 */
public class ElementString extends Element{
	private String value;

	public ElementString( String value) {
		if( value == null) throw new NullPointerException( "value can not be null");
		this.value = value;
	}
	
	@Override
	public String asText() {
		return toString();
	}
	
	public String getValue() {
		return value;
	}

	@Override
	public String toString() {
		//opet dodaj escape znakove
		String temp = value.replace("\\", "\\\\");
		temp = temp.replace("\"", "\\\"");
		return '\"' + temp + '\"';
	}
	
	
}
