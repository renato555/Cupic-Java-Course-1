package hr.fer.oprpp1.custom.collections;

/**
 * Simple map that stores key, value pairs. 
 * @author renat
 *
 * @param <K> map key class
 * @param <V> map value class
 */
public class Dictionary<K, V> {
	/**
	 * Inner collection that stores key, value pairs.
	 */
	private ArrayIndexedCollection< Pair<K, V>> innerArray;
	
	/**
	 * Default constructor.
	 */
	public Dictionary() {
		innerArray = new ArrayIndexedCollection<>();
	}
	
	/**
	 * @return true if the dictionary is empty, false otherwise
	 */
	public boolean isEmpty() {
		return innerArray.isEmpty();
	}
	
	/**
	 * @return size of the dictionary
	 */
	public int size() {
		return innerArray.size();
	}
	
	/**
	 * Removes all of the stored values from the dictionary.
	 */
	public void clear() {
		innerArray.clear();
	}
	
	/**
	 * Stores key, value pair in this dictionary.
	 * Overwrites value if the key is already present in the dictionary. 
	 * @param key
	 * @param value
	 * @return value that is overwritten, null otherwise
	 * @throws NullPointerException if key equals null
	 */
	V put( K key, V value) {
		V result = remove( key);
		innerArray.add( new Pair<>( key, value));
		
		return result;
	}
	
	/**
	 * Removes a pair from a collection that has the desired key.
	 * @param key 
	 * @return removed value, or null if nothing was removed
	 */
	V remove( K key) {
		if( key == null) return null;
		
		Pair<K, ?> newPair = new Pair<>(key, null);
		V result = get( key);
		innerArray.remove( newPair);
		
		return result;
	}
	
	/**
	 * @param key for which we wish to get the value
	 * @return appropriate value for this key, null otherwise
	 */
	V get( Object key) {
		if( key == null) return null;
		
		@SuppressWarnings({ "unchecked", "rawtypes" })
		Pair newPair = new Pair( key, null);
		V result;
		int index = innerArray.indexOf( newPair);
		if( index == -1) {
			result = null;
		}else {
			result = innerArray.get( index).value;
		}
		
		return result;
	}
	
	/**
	 * inner class that stores key, value pairs
	 * @author renat
	 *
	 * @param <A> key
	 * @param <B> value
	 */
	private static class Pair<A, B>{
		/**
		 * Stores key value
		 */
		A key;
		/**
		 * Stores value value
		 */
		B value;
		
		/**
		 * Default constructor.
		 * @param key initial key value
		 * @param value initial value value
		 * 
		 * @throws NullPointerException if key equals null
		 */
		Pair( A key, B value){
			if( key == null) throw new NullPointerException( "key ne moze biti null");
			
			this.key = key;
			this.value = value;
		}
		
		/**
		 * Two pairs are equal if their keys are equal.
		 */
		@Override
		public boolean equals(Object obj) {
			if( obj == null || !(obj instanceof Pair)) return false;
			
			@SuppressWarnings("rawtypes")
			Pair other = (Pair) obj;
			return key.equals( other.key);
		}
		
		
	}
}
