package hr.fer.oprpp1.custom.collections;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class LinkedListIndexedCollectionTest {
	
	/**
	 * pass null collection to constructor
	 */
	@Test
	void constructorOtherNull() {
		assertThrows( NullPointerException.class, ()->{ new LinkedListIndexedCollection( null);});
	}
	
	/**
	 * pass other collection to constructor
	 */
	@Test
	void constructorOtherCollection() {
		LinkedListIndexedCollection first = new LinkedListIndexedCollection();
		first.add( 1);
		first.add( 2);
		first.add( 3);
		
		LinkedListIndexedCollection second = new LinkedListIndexedCollection( first);
		assertArrayEquals( new Object[] {Integer.valueOf( 1), Integer.valueOf(2), Integer.valueOf( 3)}, second.toArray());
	}
	
	/**
	 * add baca NullPointer
	 */
	@Test
	void addNullPointer() {
		LinkedListIndexedCollection arr = new LinkedListIndexedCollection();
		assertThrows( NullPointerException.class, ()->{arr.add( null);});
	}
	
	/**
	 * 100 add calls
	 */
	@Test
	void add100Elements() {
		LinkedListIndexedCollection arr = new LinkedListIndexedCollection();
		try {
			for( int i = 0; i < 100; ++i) {
				arr.add( i);
			}
		}catch(Exception e) {
			fail( "add method can't add 100 elements");
		}
	}
	
	/**
	 * get element on index >= size
	 */
	@Test
	void getWrongIndex() {
		LinkedListIndexedCollection arr = new LinkedListIndexedCollection();
		arr.add( 1);
		arr.add( 2);
		arr.add( 3);
		
		assertThrows( IndexOutOfBoundsException.class, ()->{arr.get( 3);});
	}
	
	/**
	 * get element on index < 0
	 */
	@Test
	void getWrongIndex2() {
		LinkedListIndexedCollection arr = new LinkedListIndexedCollection();
		arr.add( 1);
		arr.add( 2);
		arr.add( 3);
		
		assertThrows( IndexOutOfBoundsException.class, ()->{arr.get( -5);});
	}

	/**
	 * checks if get returns right elements
	 */
	@Test
	void getRightIndex() {
		LinkedListIndexedCollection arr = new LinkedListIndexedCollection();
		arr.add( 1);
		arr.add( 2);
		arr.add( 3);
		
		assertEquals( 1, arr.get( 0));
		assertEquals( 2, arr.get( 1));
		assertEquals( 3, arr.get( 2));
	}

	/**
	 * clear sets size to 0
	 */
	@Test
	void clearSizeSet() {
		LinkedListIndexedCollection arr = new LinkedListIndexedCollection();
		arr.add( 1);
		arr.add( 2);
		arr.add( 3);
		
		arr.clear();
		assertEquals( 0, arr.size());
	}

	/**
	 * adding after clear
	 */
	@Test
	void addAfterClear() {
		LinkedListIndexedCollection arr = new LinkedListIndexedCollection();
		arr.add( 1);
		arr.add( 2);
		arr.add( 3);
		
		arr.clear();
		
		arr.add( 1);
		arr.add( 2);
		arr.add( 3);
		assertArrayEquals( new Object[] {Integer.valueOf( 1), Integer.valueOf( 2), Integer.valueOf( 3)}, arr.toArray());
	}

	/**
	 * insert instead of add
	 */
	@Test
	void insert5Elem() {
		LinkedListIndexedCollection arr = new LinkedListIndexedCollection();
		arr.insert( 1, 0);
		arr.insert( 2, 1);
		arr.insert( 3, 2);
		arr.insert( 4, 0);
		arr.insert( 5, 2);
		assertArrayEquals( new Object[] {Integer.valueOf( 4), Integer.valueOf( 1), Integer.valueOf( 5), Integer.valueOf( 2), Integer.valueOf( 3)}, arr.toArray());
	}
	/**
	 * insert null elem
	 */
	@Test
	void insertNull() {
		LinkedListIndexedCollection arr = new LinkedListIndexedCollection();

		assertThrows( NullPointerException.class, ()->{arr.insert(null, 0);});
	}
	
	/**
	 * insert out of bounds
	 */
	@Test
	void insertOutOfBounds() {
		LinkedListIndexedCollection arr = new LinkedListIndexedCollection();
		
		assertThrows( IndexOutOfBoundsException.class, ()->{arr.insert(2, -1);});
	}
	
	/**
	 * check that indexOf returns -1
	 */
	@Test
	void checkIndexOf() {
		LinkedListIndexedCollection arr = new LinkedListIndexedCollection();
		arr.add(0);
		arr.add(1);
		assertEquals(-1, arr.indexOf( 5));
	}
	/**
	 * check that indexOf returns -1 for null
	 */
	@Test
	void checkIndexOfNull() {
		LinkedListIndexedCollection arr = new LinkedListIndexedCollection();
		arr.add(0);
		arr.add(1);
		assertEquals(-1, arr.indexOf( null));
	}
	/**
	 * check if indexOf return right values
	 */
	@Test
	void checkIndexOfReturn() {
		LinkedListIndexedCollection arr = new LinkedListIndexedCollection();
		arr.add(0);
		arr.add(1);
		arr.add(2);
		assertEquals( 0, arr.indexOf( 0));
		assertEquals( 1, arr.indexOf( 1));
		assertEquals( 2, arr.indexOf( 2));
	}
	
	/**
	 * check if remove IndexOutOfBounds
	 */
	@Test
	void removeOutOfBounds() {
		LinkedListIndexedCollection arr = new LinkedListIndexedCollection();
		arr.add(1);
		assertThrows( IndexOutOfBoundsException.class, ()->{arr.remove( 3);});
	}
	
	/**
	 * check if remove IndexOutOfBounds
	 */
	@Test
	void removeOutOfBound2s() {
		LinkedListIndexedCollection arr = new LinkedListIndexedCollection();
		arr.add(1);
		assertThrows( IndexOutOfBoundsException.class, ()->{arr.remove( -5);});
	}
	
	/**
	 * check if remove removes value
	 */
	@Test
	void removeValue() {
		LinkedListIndexedCollection arr = new LinkedListIndexedCollection();
		arr.add(1);
		arr.add(2);
		arr.add(3);
		arr.add(4);
		arr.add(5);
		arr.remove( Integer.valueOf(2));
		arr.remove( Integer.valueOf(4));
		
		assertArrayEquals( new Object[] {Integer.valueOf( 1), Integer.valueOf( 3), Integer.valueOf( 5)}, arr.toArray());
	}
	
	/**
	 * insert and add mix
	 */
	@Test
	void insertAndAddMix() {
		LinkedListIndexedCollection arr = new LinkedListIndexedCollection();
		arr.insert(1, 0);
		arr.add(2);
		arr.insert(3, 2);
		arr.add( 4);
		
		assertArrayEquals( new Object[] {Integer.valueOf( 1), Integer.valueOf(2), Integer.valueOf( 3), Integer.valueOf( 4)}, arr.toArray());
	}
	
	/**
	 * index of after insert
	 */
	@Test
	void indexOfAfterInsert() {
		LinkedListIndexedCollection arr = new LinkedListIndexedCollection();
		arr.insert(0, 0);
		arr.insert(1, 0);
		
		assertEquals( 1, arr.indexOf( Integer.valueOf(0)));
	}
	
	/**
	 * remove and then insert
	 */
	@Test
	void removeAndThenInsert() {
		LinkedListIndexedCollection arr = new LinkedListIndexedCollection();
		arr.add(1);
		arr.add(2);
		arr.add(3);
		
		arr.remove( Integer.valueOf( 2));
		arr.insert(2, 1);
		assertArrayEquals( new Object[] {Integer.valueOf( 1), Integer.valueOf(2), Integer.valueOf( 3)}, arr.toArray());
	}
}
