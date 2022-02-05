package hr.fer.oprpp1.custom.collections;

/**
 * Interfece for collection iterators.
 * @author renat
 *
 */
public interface ElementsGetter {
	/**
	 * @return true if there are more elements in collection, false otherwise
	 */
	boolean hasNextElement();
	
	/** 
	 * @return next element in the collection
	 */
	Object getNextElement();
	
	/**
	 * Applies Processor::process function on all remaining elements.
	 * @param p Processor with desired process function
	 * @throws NullPointerException if p equals null
	 */
	default void processRemaining( Processor p) {
		if( p == null) throw new NullPointerException( "processor ne moze biti null");
		
		while( hasNextElement()) {
			p.process( getNextElement());
		}
	}
}
