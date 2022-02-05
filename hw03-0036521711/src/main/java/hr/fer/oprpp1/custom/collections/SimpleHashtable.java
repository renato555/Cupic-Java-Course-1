package hr.fer.oprpp1.custom.collections;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Simple map that stores key, value pairs.
 * @author renat
 *
 * @param <K> map key class
 * @param <V> map value class
 */
public class SimpleHashtable<K, V> implements Iterable< SimpleHashtable.TableEntry<K, V>>{
	/**
	 * Current size of the map.
	 */
	private int size;
	/**
	 * Inner array that stores key, value entries.
	 */
	private TableEntry<K, V>[] innerArr;
	/**
	 * Long that keep count how many times this map has been modified.
	 */
	private long modificationCount;
	
	/**
	 * Default constructor. Sets initial capacity to 16.
	 */
	public SimpleHashtable() {
		this( 16);
	}
	
	/**
	 * Constructor that sets initial capacity.
	 * Capacity will be set to the next power of 2 that is greater or equal to the passed in capacity.
	 * @param capacity desired initial capacity
	 * @throws IllegalArgumentException if capacity is less than 1
	 */
	@SuppressWarnings("unchecked")
	public SimpleHashtable( int capacity) {
		if( capacity < 1) throw new IllegalArgumentException( "capacity ne moze biti < 1");
		
		size = 0;
		modificationCount = 0;
		innerArr = (TableEntry<K, V>[]) new TableEntry[ nextPowerOf2( capacity)];
	}
	
	/**
	 * @param n
	 * @return next power of 2 that is greater or equal than n
	 */
	private int nextPowerOf2( int n) {
		int exponent = (int) Math.ceil( Math.log(n) / Math.log(2));
		return (int) Math.pow(2, exponent);
	}
	
	/**
	 * @param obj for which we want to get appropriate index in the array
	 * @return absolute value of obj hash value % inner array length
	 */
	private int hashFunction( Object obj) {
		return Math.abs( obj.hashCode() % innerArr.length);
	}
	
	/**
	 * Doubles capacity of inner array.
	 */
	@SuppressWarnings("unchecked")
	private void doubleInSize() {
		TableEntry<K, V>[] temp = this.toArray();
		// alociraj veci niz
		innerArr = (TableEntry<K, V>[]) new TableEntry[ innerArr.length*2];
		
		size = 0;
		long savedModCount = modificationCount;
		// dodaj stare vrijednosti
		for( int i = 0; i < temp.length; ++i) {
			temp[i].next = null;
			put( temp[i]);
		}
		modificationCount = savedModCount + 1;
	}
	
	/**
	 * Stores new entry in this dictionary.
	 * @param newEntry
	 * @return value that is overwritten, null otherwise
	 */
	private V put( TableEntry<K, V> newEntry) {
		int index = hashFunction( newEntry.key);
		V result;
		
		if( innerArr[ index] == null) {
			// key ne postoji u mapi
			innerArr[index] = newEntry;
			result = null;
			++size;
			++modificationCount;
		}else {
			// key postoji
			TableEntry<K, V> temp = innerArr[index];
			TableEntry<K, V> prev = temp;
			while( temp != null && !temp.key.equals( newEntry.key)) {
				prev = temp;
				temp = temp.next;
			}
			
			if( temp == null) {
				// nema istih kljuceva, umetni na kraj liste
				prev.next = newEntry;
				result = null;
				++size;
				++modificationCount;
			}else {
				// prepisi vrijednost
				result = temp.value;
				temp.value = newEntry.value;
			}
		}

		return result;
	}
	
	/**
	 * Stores key, value pair in this dictionary.
	 * Overwrites value if the key is already present in the dictionary. 
	 * @param key
	 * @param value
	 * @return value that is overwritten, null otherwise
	 * @throws NullPointerException if key equals null
	 */
	public V put( K key, V value) {
		TableEntry<K, V> newEntry = new TableEntry<>( key, value);
		
		// provjeri jel dobar omjer velicine i kapaciteta
		if( ((double) size())/ innerArr.length >= 0.75 && !containsKey(key)) {	
			doubleInSize();
		}
		return put( newEntry);
	}
	
	/**
	 * @param key for which we wish to get the value
	 * @return appropriate value for this key, null otherwise
	 */
	public V get( Object key) {
		if( key == null) return null;
		int index = hashFunction( key);
		
		TableEntry<K, V> temp = innerArr[index];
		while( temp != null && !temp.key.equals( key)) {
			temp = temp.next;
		}
		
		if( temp == null) {
			return null;
		}else {
			return temp.value;
		}
	}
	/**
	 * @return total number of elements stores in the map
	 */
	public int size() {
		return size;
	}
	
	/**
	 * @param key
	 * @return true if key is present in this map, false otherwise
	 */
	public boolean containsKey( Object key) {
		if( key == null) return false;
		int index = hashFunction( key);
		
		TableEntry<K, V> temp = innerArr[index];
		while( temp != null) {
			if( temp.key.equals(key)) {
				return true;
			}
			temp = temp.next;
		}
		
		return false;
	}
	
	/**
	 * @param value
	 * @return true if value is present in this map, false otherwise
	 */
	public boolean containsValue( Object value) {
		for( int i = 0, n = innerArr.length; i < n; ++i) {
			TableEntry<K, V> temp = innerArr[ i];
			while( temp != null) {
				if( temp.value == null && value == null) {
					return true;
				}else if( temp.value != null && temp.value.equals( value)) {
					return true;
				}
				temp = temp.next;
			}
		}
		return false;
	}
	
	/**
	 * Removes a pair from a collection that has the desired key.
	 * @param key 
	 * @return removed value, or null if nothing was removed
	 */
	public V remove( Object key) {
		if( key == null) return null;
		int index = hashFunction( key);
		V result;
		
		if( innerArr[index] == null) {
			// ne postoji takav kljuc u listi
			result = null;
		}else if( innerArr[index].key.equals( key)){
			// to je prvi element u listi
			--size;
			++modificationCount;
			result = innerArr[index].value;
			innerArr[index] = innerArr[index].next;
		}else {
			// mozda kljuc je negdje dublje u listi
			TableEntry<K, V> prev = innerArr[index];
			while( prev.next != null && !prev.next.key.equals( key)) {
				prev = prev.next;
			}
			
			if( prev.next == null) {
				// ne postoji taj kljuc
				result = null;
			}else {
				// prev.next ima taj kljuc
				--size;
				++modificationCount;
				result = prev.next.value;
				prev.next = prev.next.next;
			}
		}
		
		return result;
	}
	
	/**
	 * @return true if the collection is empty, false otherwise
	 */
	public boolean isEmpty() {
		return size() == 0;
	}
	
	/**
	 * Format example: [key1=value1, key2=value2, key3=value3]
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append( "[");
		for( int i = 0, n = innerArr.length; i < n; ++i) {
			TableEntry<K, V> temp = innerArr[ i];
			while( temp != null) {
				builder.append( temp.toString());
				builder.append( ", ");
				temp = temp.next;
			}
		}
		String result = builder.toString();
		// makni na kraju ", " ako ima
		if( result.endsWith(", ")) {
			result = result.substring(0, result.length() - 2);
		}
		return result + "]";
	}
	
	/**
	 * @return an array with references to all TableEntries stored in this map
	 * @throws UnsupportedOperationException if size is less than 1
	 */
	@SuppressWarnings({ "unchecked" })
	public TableEntry<K, V>[] toArray(){
		if( size() < 1) {
			throw new UnsupportedOperationException( "mapa ne sadrzi niti jedan element");
		}
		TableEntry<K, V>[] result = (TableEntry<K, V>[]) new TableEntry[ size()];
		
		int j = 0;
		for( int i = 0, n = size(); j < n; ++i) {
			TableEntry<K, V> temp = innerArr[i];
			while( temp != null) {
				result[j++] = temp;
				temp = temp.next;
			}
		}
		
		return result;
	}
	
	/**
	 * Removes all elements for this map.
	 */
	public void clear() {
		for( int i = 0; i < innerArr.length; ++i) {
			innerArr[i] = null;
		}
		++modificationCount;
		size = 0; 
	}

	/**
	 * Class that stores key, value pairs.
	 * @author renat
	 *
	 * @param <K> key
	 * @param <V> value
	 */
	public static class TableEntry<K, V>{
		/**
		 * Stores key value.
		 */
		private K key;
		/**
		 * Stores value value.
		 */
		private V value;
		/**
		 * Reference to next TableEntry in a list.
		 */
		private TableEntry<K, V> next;
		
		/**
		 * TableEntry constructor
		 * @param key
		 * @param value
		 * @throws NullPointerException if key equals null
		 */
		public TableEntry( K key, V value) {
			if( key == null) throw new NullPointerException( "key ne moze biti null");
			
			this.key = key;
			this.value = value;
			next = null;
		}

		public K getKey() {
			return key;
		}

		public V getValue() {
			return value;
		}

		public void setValue(V value) {
			this.value = value;
		}

		@Override
		public String toString() {
			String valString = value == null ? "null" : value.toString();
			return key.toString()+"="+valString;
		}
	}
	
	/**
	 * @return iterator for this map.
	 */
	public Iterator< SimpleHashtable.TableEntry<K, V>> iterator(){
		return new IteratorImpl( this);
	}
	
	/**
	 * Class that implements iterator for this map.
	 * Elements are returned from the beginning of inner array and from left to right if they are in a list.  
	 * @author renat
	 */
	private class IteratorImpl implements Iterator<SimpleHashtable.TableEntry<K, V>>{
		/**
		 * A reference to map perent.
		 */
		private SimpleHashtable<K, V> table;
		/**
		 * Total number of elements stores in parent map when this iterator was created.
		 */
		private int numberOfElements;
		/**
		 * Total number of returned elements.
		 */
		private int numberOfReturnedElements;
		/**
		 * Total number of modification in parent map when this iterator was created.
		 */
		private long savedModificationCount;
		
		/**
		 * A reference to last returned TableEntry.
		 * Null before first call to next()
		 * Null after remove and current was last element in a list
		 * A reference to a new element when last removed element was not last in the list
		 */
		private SimpleHashtable.TableEntry<K, V> current;
		/**
		 * Index of last returned element in inner array.
		 */
		private int lastIndex;
		/**
		 * true if current was deleted by this iterator, false otherwise
		 */
		private boolean deleted;
		
		/**
		 * Initialises all attributes.
		 * @param table parent map
		 */
		private IteratorImpl( SimpleHashtable<K, V> table) {
			this.table = table;
			numberOfElements = table.size();
			current = null;
			lastIndex = -1;
			savedModificationCount = table.modificationCount;
			deleted = false;
		}
		
		/**
		 * @return true if iterator can return more element, false otherwise
		 * @throws ConcurrentModificationException if savedModificationCount does not equal modificationCount of parent map
		 */
		public boolean hasNext() {
			if( savedModificationCount != table.modificationCount) throw new ConcurrentModificationException( "the collection has been changed");
			
			return numberOfReturnedElements < numberOfElements;
		}
		
		/**
		 * @return next TableEntry in the map.
		 * @throws ConcurrentModificationException if savedModificationCount does not equal modificationCount of parent map
		 */
		public SimpleHashtable.TableEntry<K, V> next(){
			if( !hasNext()) throw new NoSuchElementException( "no more elements");

			if( current == null || (!deleted && current.next == null)) {
				// trazi sljedecu listu
				for( int i = lastIndex+1, n = table.innerArr.length; i < n; ++i) {
					if( table.innerArr[i] != null) {
						current = table.innerArr[i];
						lastIndex = i;
						break;
					}
				}
			}else if( !deleted) {
				// vrati sljedeceg u listi
				current = current.next;
			}
			
			deleted = false;
			++numberOfReturnedElements;
			return current;
		}
		
		/**
		 * Removes last TableEntry that was returned by next().
		 * @throws ConcurrentModificationException if savedModificationCount does not equal modificationCount of parent map
		 * @throws IllegalStateException if the next method has not yet been called, or the remove method has already been called after the last call to the next method
		 */
		public void remove() {
			if( savedModificationCount != table.modificationCount) throw new ConcurrentModificationException( "the collection has been changed");
			if( deleted) throw new IllegalStateException( "metoda remove se ne moze zvati dvaput za redom");
			if( current == null)  throw new IllegalStateException( "metoda se ne moze zvati prije metode next");
				
			SimpleHashtable.TableEntry<K, V> temp = current;
			current = current.next;
			deleted = true;
			table.remove( temp.key);
			
			++savedModificationCount;
		}
	}
}
