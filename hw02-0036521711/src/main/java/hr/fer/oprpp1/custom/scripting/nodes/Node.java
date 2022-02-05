package hr.fer.oprpp1.custom.scripting.nodes;

import hr.fer.oprpp1.custom.collections.ArrayIndexedCollection;
import hr.fer.oprpp1.custom.collections.ElementsGetter;
import hr.fer.oprpp1.custom.scripting.elems.Element;

/**
 * Use for a representation of a structured document.
 * @author renat
 *
 */
public class Node {
	/**
	 * Contains all children nodes.
	 */
	private ArrayIndexedCollection arr;
	
	/**
	 * Adds new node to arr.
	 * @param child to be added
	 * @throws NullPointerException if child equals null
	 */
	public void addChildNode( Node child) {
		if( child == null) throw new NullPointerException( "child ne moze biti null");
		if( arr == null) arr = new ArrayIndexedCollection();
		
		arr.add( child);
	}
	
	/**
	 * @return number of child nodes.
	 */
	public int numberOfChildren() {
		if( arr == null) return 0;
		
		return arr.size();
	}
	
	/**
	 * Returns child node at a given index.
	 * @param index desired position in an array
	 * @return Node at a given index
	 * @throws IndexOutOfBoundsException - if index < 0 || index > size-1
	 */
	Node getChild( int index) {
		return (Node) arr.get( index);
	}

	@Override
	public String toString() {
		String result = "";
		if( arr != null) {
			ElementsGetter iterator = arr.createElementsGetter();
			while( iterator.hasNextElement()) {
				Node nextElem = (Node) iterator.getNextElement();
				result += nextElem.toString() + " ";
			}
		}
		return result;
	}
	
	
}
