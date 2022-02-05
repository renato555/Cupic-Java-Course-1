package hr.fer.oprpp1.custom.collections;

import java.util.Arrays;

/**
 * Extends Collection.
 * Resizable array collection.
 * 
 * @author renat
 */
public class ArrayIndexedCollection extends Collection{
	/**
	 * Number of elements in the <code>elements</code> array.
	 */
	private int size;
	/**
	 * Array that stores elements.
	 */
	private Object[] elements;
	
	/**
	 * Constructor that sets collection capacity to 16. 
	 */
	public ArrayIndexedCollection() {
		this( null, false, 16);
	}
	/**
	 * Sets the initial capacity of the elements array equal to passed parameter.
	 * @param initialCapacity initial size of elements array
	 * @throws IllegalArgumentException if the <code>initialCapacity</code> is < 1
	 */
	public ArrayIndexedCollection( int initialCapacity) {
		this( null, false, initialCapacity);
	}
	/**
	 * Fills this collection with elements from the <code>other</code> collection.
	 * @param other collection with elements we want to add to this collection
	 * @throws NullPointerException if <code>other == null</code>
	 */
	public ArrayIndexedCollection( Collection other) {
		this( other, true, 16);
	}
	/**
	 * Fills this collection with elements from the <code>other</code> collection and sets
	 * the capacity to initialCapacity, unless other collection has more elements than initialCapacity
	 * then it is set to the size of other collection.
	 * 
	 * @param other collection with elements we want to add to this collection
	 * @param initialCapacity initial size of elements array
	 * @throws NullPointerException if <code>other == null</code>
	 * @throws IllegalArgumentException if <code>initialCapacity</code> is < 1 and other collection has no elements
	 */
	public ArrayIndexedCollection( Collection other, int initialCapacity) {
		this( other, true, initialCapacity);
	}
	/**
	 * Common constructor for all 4 public constructors. 
	 * If checkOther is true then we take other collection's size into consideration when determining
	 * initialCapacity and add all elements from the collection into this collection.
	 * 
	 * @param other collection with elements we want to add
	 * @param checkOther <strong>true</strong> if the user wants to add elements from the collection, <strong>false</strong> otherwise
	 * @param initialCapacity 
	 */
	private ArrayIndexedCollection( Collection other, boolean checkOther, int initialCapacity){
		//if true set elements.length to max( initialCapacity, other.size)  
		if( checkOther) {
			if( other == null) throw new NullPointerException( "other collection can not be null");
			initialCapacity = initialCapacity >= other.size() ? initialCapacity : other.size();
		}
		if( initialCapacity < 1) throw new IllegalArgumentException( "initial capacity can not be < 1");
		elements = new Object[ initialCapacity];
		
		//if true add elements from the other collection
		if( checkOther) {
			addAll( other);
		}
	}
	
	/**
	 * @return {@inheritDoc}
	 */
	public int size() {
		return size;
	}
	/**
	 * {@inheritDoc}
	 * If there is not enough size in elements array, it will double in size and then add the element.
	 * Elements are added at the end of the array.
	 * @throws NullPointerException if value == null
	 */
	public void add( Object value) {
		if( value == null) throw new NullPointerException( "other value can not be null");
		if( size + 1 > elements.length) {
			doubleInSize();
		}
		
		elements[ size] = value;
		++size;
	}
	/**
	 * Elements array doubles in size.
	 */
	private void doubleInSize() {
		elements = Arrays.copyOf(elements, size*2);
	}
	/**
	 * 
	 * @param index of the desired element
	 * @return element at index
	 * @throws IndexOutOfBoundsException if <code>index < 0 || index > size-1</code>
	 */
	public Object get( int index) {
		if( index < 0 || index > size-1) throw new IndexOutOfBoundsException( "index must be between 0 and size-1");
		
		return elements[index];
	}
	/**
	 * {@inheritDoc}
	 */
	public void clear() {
		for( int i = 0; i < size; ++i) {
			elements[i] = null;
		}
		size = 0;
	}
	/**
	 * 
	 * Inserts an element at a given position.
	 * 
	 * @param value to be inserted
	 * @param position at which new elements should be inserted
	 * @throws IndexOutOfBoundsException if <code>position < 0 || position > size</code>
	 * @throws NullPointerException if new element is null
	 */
	public void insert( Object value, int position) {
		if( position < 0 || position > size) throw new IndexOutOfBoundsException( "position can be bewteen 0 and size");
		if( value == null) throw new NullPointerException( "other value can not be null");
		if( size + 1 > elements.length) doubleInSize();
		
		for( int i = size; i > position; --i) {
			elements[ i] = elements[ i-1];
		}
		elements[ position] = value;
		++size;
	}
	/**
	 * Returns the index of the first occurrence of the given value.
	 * Equality is determined by using equals method.
	 * @param value for which we wish to get the index
	 * @return index of the input value or -1 if it is not found
	 */
	public int indexOf( Object value) {
		if( value == null) return -1;
		
		for( int i = 0; i < size; ++i) {
			if( elements[i].equals( value)) {
				return i;
			}
		}
		
		return -1;
	}
	
	/**
	 * Removes an element at index and shifts elements that where at greater index one
	 * spot to the left.
	 * @param index of element that need to be removed
	 * @throws IndexOutOfBoundsException if <code>index < 0 || index >= size</code>
	 */
	public void remove( int index) {
		if( index < 0 || index >= size) throw new IndexOutOfBoundsException( "index can be bewteen 0 and size-1");
		
		for( int i = index; i < size - 1; ++i) {
			elements[i] = elements[i+1];
		}
		elements[size-1] = null;
		
		--size;
	}
	/**
	 * {@inheritDoc}
	 */
	public boolean remove( Object value) {
		if( value == null) return false;
		
		for( int i = 0; i < size; ++i) {
			if( value.equals( elements[i])) {
				remove( i);
				return true;
			}
		}
		
		return false;
	}
	
	public boolean contains( Object value) {
		if( value == null) return false;
		
		for( int i = 0; i < size; ++i) {
			if( elements[i].equals( value)) {
				return true;
			}
		}
		
		return false;
	}
	/**
	 * @throws UnsupportedOperationException if the collection does not contain any elements
	 */
	public Object[] toArray() {
		if( size < 1) {
			super.toArray();
		}
		
		return Arrays.copyOf( elements, size);
	}
	
	public void forEach( Processor processor) {
		for( int i = 0; i < size; ++i) {
			processor.process( elements[i]);
		}
	}
}
