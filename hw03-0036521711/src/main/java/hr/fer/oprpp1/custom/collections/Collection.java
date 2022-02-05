package hr.fer.oprpp1.custom.collections;

import java.util.function.Function;

/**
 *  Represents a general collection of objects.
 *  
 * @author renat
 *
 */
public interface Collection<T>{
	/**
	 * 
	 * @return <strong>true</strong> if the collection is empty, <strong>false</strong> otherwise
	 */
	default boolean isEmpty() {
		return size() == 0;
	}
	/**
	 * 
	 * @return number of objects stored in the collection
	 */
	int size();
	/**
	 * Adds an element into the collection.
	 * @param value to be added 
	 */
	void add( T value);
	/**
	 * 
	 * @param value to be checked  
	 * @return <strong>true</strong> if passed parameter is in the collection, <strong>false</strong> otherwise
	 */
	boolean contains( Object value);
	/**
	 * Removes the first element from the collection that is the same as the passed parameter
	 * determined by <code> equals </code> method.
	 * 
	 * @param value to be removed
	 * @return <strong>true</strong> if element was removed successfully, <strong>false</strong> otherwise 
	 */
	boolean remove( Object value);
	/**
	 * Allocates new array with size equal to the size of this collection, fills it with collection content and
	 * returns the array. It does not create new copies of objects, instead it fills the array with references to
	 * existing objects.
	 * @return array of objects
	 * @throws UnsupportedOperationException if the collection does not contain any elements
	 */
	Object[] toArray();
	/**
	 * 
	 * @param processor to be called for each element in the collection
	 */
	default void forEach( Processor<? super T> processor) {
		ElementsGetter<T> iterator = createElementsGetter();
		while( iterator.hasNextElement()) {
			processor.process( iterator.getNextElement());
		}
	}
	
	/**
	 * Adds all elements from the other collection to this collection.
	 * 
	 * @param other collection that contains the elements we want to add
	 */
	default void addAll( Collection<? extends T> other) {
		class AddToCollection implements Processor<T>{
			public void process( T value){
				add( value);
			};
		}
		
		other.forEach( new AddToCollection());
	}
	
	/**
	 * Removes all elements from the collection.
	 */
	void clear();
	
	/**
	 * Returns new iterator for this collection
	 * @return iterator
	 */
	ElementsGetter<T> createElementsGetter();
	
	/**
	 * Adds all elements from col to this collection for which tester returns true. 
	 * @param col contains new elements
	 * @param tester predicate on which elements are tested
	 * @throws NullPointerException if col or tester equal null
	 */
	default void addAllSatisfying( Collection<? extends T> col, Tester<? super T> tester) {
		if( col == null) throw new NullPointerException( "col ne moze biti null");
		if( tester == null) throw new NullPointerException( "tester ne moze biti null");
		
		ElementsGetter<? extends T> otherIterator = col.createElementsGetter();
		while( otherIterator.hasNextElement()) {
			T nextElem = otherIterator.getNextElement();
			if( tester.test( nextElem)) {
				add( nextElem);
			}
		}
	}
	
	default <E> void addModified( Collection<E> col, Function<? super E, ? extends T> transfomer) {
		if( col == null) throw new NullPointerException( "col ne moze biti null");
		if( transfomer == null) throw new NullPointerException( "tester ne moze biti null");
		
		ElementsGetter<E> otherIterator = col.createElementsGetter();
		while( otherIterator.hasNextElement()) {
			E nextElem = otherIterator.getNextElement();
			add( transfomer.apply( nextElem));
		}
	}
}
