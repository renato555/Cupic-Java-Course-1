package hr.fer.oprpp1.custom.collections;

/**
 * Extends collection interface.
 * @author renat
 *
 */
public interface List extends Collection{
	/**
	 * Returns object at desired index.
	 * @param index of the desired element
	 * @return element at index
	 */
	Object get( int index);
	
	/**
	 * Inserts an element at a given position.
	 * @param value to be inserted
	 * @param position at which new elements should be inserted
	 */
	void insert( Object value, int position);
	
	/**
	 * Returns the index of the first occurrence of the given value.
	 * Equality is determined by using equals method.
	 * @param value for which we wish to get the index
	 * @return index of the input value or -1 if it is not found
	 */
	int indexOf( Object value);
	
	/**
	 * Removes an element at index and shifts elements that had greater index one
	 * spot to the left.
	 * @param index of element that need to be removed
	 */
	void remove( int index);
}
