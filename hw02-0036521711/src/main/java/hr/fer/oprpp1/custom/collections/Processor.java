package hr.fer.oprpp1.custom.collections;

/**
 * Classes that implement Processor have to implement <code>process</code> method
 * which is capable of performing some operation on the passed object.
 * 
 * @author renat
 */
public interface Processor {
	/**
	 * Processes passed in element.
	 * @param value element
	 */
	void process( Object value);
}
