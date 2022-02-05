package hr.fer.oprpp1.custom.scripting.nodes;

import hr.fer.oprpp1.custom.scripting.elems.Element;

/**
 * A node that represents a command which generates some textual output dynamically.
 * @author renat
 *
 */
public class EchoNode extends Node {
	/**
	 * Contains all elements in a tag
	 */
	private Element[] elements;
	
	/**
	 * Default constructor
	 * @param elements an array with child elements
	 * @throws NullPointerException if elements equals null
	 */
	public EchoNode( Element[] elements) {
		if( elements == null) throw new NullPointerException( "elements ne moze biti null");
		this.elements = elements;
	}
	
	/**
	 * @return array with child elements
	 */
	public Element[] getElements() {
		return elements;
	}

	@Override
	public String toString() {
		String result = "{$= ";
		for( int i = 0; i < elements.length; i++) {
			result += elements[i].toString() + " ";
		}
		return result + "$}";
	}
	
	
}
