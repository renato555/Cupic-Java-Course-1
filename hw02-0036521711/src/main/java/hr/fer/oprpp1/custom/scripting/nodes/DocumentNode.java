package hr.fer.oprpp1.custom.scripting.nodes;

/**
 * A node that represents an entire document.
 * @author renat
 *
 */
public class DocumentNode extends Node {

	@Override
	public String toString() {
		return super.toString();
	}

	@Override
	public boolean equals(Object obj) {
		if( obj == null || !(obj instanceof DocumentNode)) return false;
		DocumentNode other = (DocumentNode) obj;
		return other.toString().equals( this.toString());
	}
}
