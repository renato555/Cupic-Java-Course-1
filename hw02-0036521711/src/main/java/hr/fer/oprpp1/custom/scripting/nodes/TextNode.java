package hr.fer.oprpp1.custom.scripting.nodes;

/**
 * A node that represents a piece of textual data.
 * @author renat
 */
public class TextNode extends Node{
	/**
	 * Stored text value.
	 */
	private String text;
	
	/**
	 * Default constructor
	 * @param text value to be stored
	 * @throws NullPointerException if text equals null
	 */
	public TextNode( String text) {
		if( text == null) throw new NullPointerException( "text ne moze biti null");
		this.text = text;
	}
	
	public String getText() {
		return text;
	}

	@Override
	public String toString() {
		//dodaj opet escape znakove
		String temp = text.replace("\\", "\\\\");
		temp = temp.replace("{", "\\{");
		return temp;
	}
	
	
}
