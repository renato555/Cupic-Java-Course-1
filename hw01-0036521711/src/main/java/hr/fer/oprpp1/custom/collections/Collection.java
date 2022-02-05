package hr.fer.oprpp1.custom.collections;

/**
 *  Represents a general collection of objects.
 *  
 * @author renat
 *
 */
public class Collection {
	protected Collection(){};
	
	/**
	 * 
	 * @return <strong>true</strong> if the collection is empty, <strong>false</strong> otherwise
	 */
	public boolean isEmpty() {
		return size() == 0;
	}
	/**
	 * 
	 * @return number of objects stored in the collection
	 */
	public int size() { 
		return 0;
	}
	/**
	 * Adds an element into the collection.
	 * @param value to be added 
	 */
	public void add( Object value) {
		
	}
	/**
	 * 
	 * @param value to be checked  
	 * @return <strong>true</strong> if passed parameter is in the collection, <strong>false</strong> otherwise
	 */
	public boolean contains( Object value) {
		return false;
	}
	/**
	 * Removes the first element from the collection that is the same as the passed parameter
	 * determined by <code> equals </code> method.
	 * 
	 * @param value to be removed
	 * @return <strong>true</strong> if element was removed successfully, <strong>false</strong> otherwise 
	 */
	public boolean remove( Object value) {
		return false;
	}
	/**
	 * Allocates new array with size equal to the size of this collection, fills it with collection content and
	 * returns the array. It does not create new copies of objects, instead it fills the array with references to
	 * existing objects.
	 * @return array of objects
	 * @throws UnsupportedOperationException if the collection does not contain any elements
	 */
	public Object[] toArray() {
		throw new UnsupportedOperationException( "Collection does not contain any elements");
	}
	/**
	 * 
	 * @param processor to be called for each element in the collection
	 */
	public void forEach( Processor processor) {
		
	}
	
	/**
	 * Adds all elements from the other collection to this collection.
	 * 
	 * @param other collection that contains the elements we want to add
	 */
	//TODO sta ako je value Collection?
	public void addAll( Collection other) {
		class AddToCollection extends Processor{
			public void process( Object value){
				add( value);
			};
		}
		
		other.forEach( new AddToCollection());
	}
	
	/**
	 * Removes all elements from the collection.
	 */
	public void clear( ) {
		
	}
}
