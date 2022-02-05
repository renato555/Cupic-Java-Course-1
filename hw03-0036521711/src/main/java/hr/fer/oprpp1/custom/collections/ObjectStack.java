package hr.fer.oprpp1.custom.collections;

/**
 * Stack First In Fist Out
 * @author renat
 */
public class ObjectStack<T> {
	/**
	 * Inner collection that stores stack elements.
	 */
	ArrayIndexedCollection<T> innerArray;
	
	/**
	 * Creates an empty stack.
	 */
	public ObjectStack() {
		innerArray = new ArrayIndexedCollection<>();
	}
	/**
	 * 
	 * @return <strong>true</strong> if the collection is empty, <strong>false</strong> otherwise
	 */
	public boolean isEmpty() {
		return innerArray.isEmpty();
	}
	/**
	 * 
	 * @return number of objects stored in the collection
	 */
	public int size() {
		return innerArray.size();
	}
	/**
	 * Adds an element to the top of the stack.
	 * @param value to be added
	 * @throws NullPointerException if value == null
	 */
	public void push( T value) {
		innerArray.add( value);
	}
	/**
	 * Removes the element at the top of the stack and returns it.
	 * @return element at the stop of the stack
	 * @throws EmptyStackException if the stack is empty
	 */
	public T pop(){
		if( isEmpty()) throw new EmptyStackException( "the stack is empty");
		
		int size = size();
		T result = innerArray.get( size-1);
		innerArray.remove( size-1);
		
		return result;
	}
	/**
	 * Returns the element at the top of the stack.
	 * @return element at the stop of the stack
	 * @throws EmptyStackException if the stack is empty
	 */
	public T peek(){
		if( isEmpty()) throw new EmptyStackException( "the stack is empty");
		
		int size = size();
		T result = innerArray.get( size-1);
		
		return result;
	}
	/**
	 * Removes all elements from the stack.
	 */
	public void clear() {
		innerArray.clear();
	}
}
