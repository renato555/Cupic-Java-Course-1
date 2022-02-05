package hr.fer.oprpp1.custom.collections;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ArrayIndexedCollectionTest {
	/**
	 * initial capacity < 1
	 */
	@Test
	void constructorInitialCapacity() {
		assertThrows( IllegalArgumentException.class, ()->{ new ArrayIndexedCollection(0);});
	}
	
	/**
	 * passed in other collection
	 */
	@Test
	void constructorPassedOtherCollection() {
		ArrayIndexedCollection first = new ArrayIndexedCollection( 3);
		first.add( 1);
		first.add( 2);
		first.add( 3);
		
		ArrayIndexedCollection second = new ArrayIndexedCollection( first);
		second.add( 4);
		second.add( 5);
		assertArrayEquals( new Object[] {Integer.valueOf( 1), Integer.valueOf( 2), Integer.valueOf( 3), Integer.valueOf( 4), Integer.valueOf( 5)}, second.toArray());
	}
	
	/**
	 * first collection size > initial size check
	 */
	@Test
	void constructorOtherSizeGreaterThanInitialCapacity() {
		ArrayIndexedCollection first = new ArrayIndexedCollection( 3);
		first.add( 1);
		
		ArrayIndexedCollection second = new ArrayIndexedCollection( first, 0);
		assertArrayEquals( new Object[] {Integer.valueOf( 1)}, second.toArray());
	}
	
	/**
	 * Passed null as other collection
	 */
	@Test
	void constructorPassedNull() {
		assertThrows( NullPointerException.class, ()->{ new ArrayIndexedCollection(null, 1);});
	}
	
	/**
	 * passed in other collection and capacity < 1
	 */
	@Test
	void constructorPassedOtherAndWrongCapacity() {
		ArrayIndexedCollection first = new ArrayIndexedCollection( 3);
		first.add( 1);
		first.add( 2);
		first.add( 3);
		try {
			ArrayIndexedCollection second = new ArrayIndexedCollection( first, -3);
		}catch( Exception e) {
			fail( "initial capacity should have been overridden by other collecion's size");
		}
	}

	/**
	 * add baca NullPointer
	 */
	@Test
	void addNullPointer() {
		ArrayIndexedCollection arr = new ArrayIndexedCollection();
		assertThrows( NullPointerException.class, ()->{arr.add( null);});
	}
	
	/**
	 * 100 add calls
	 */
	@Test
	void add100Elements() {
		ArrayIndexedCollection arr = new ArrayIndexedCollection();
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
		ArrayIndexedCollection arr = new ArrayIndexedCollection( 3);
		arr.add( 1);
		arr.add( 2);
		arr.add( 3);
		
		assertThrows( IndexOutOfBoundsException.class, ()->{arr.get( 3);});
	}
	/**
	 * get wrong on index < 0
	 */
	@Test
	void getWrongIndex2() {
		ArrayIndexedCollection arr = new ArrayIndexedCollection( 3);
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
		ArrayIndexedCollection arr = new ArrayIndexedCollection( 3);
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
		ArrayIndexedCollection arr = new ArrayIndexedCollection( 3);
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
		ArrayIndexedCollection arr = new ArrayIndexedCollection( 3);
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
		ArrayIndexedCollection arr = new ArrayIndexedCollection( 3);
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
		ArrayIndexedCollection arr = new ArrayIndexedCollection();

		assertThrows( NullPointerException.class, ()->{arr.insert(null, 0);});
	}
	
	/**
	 * insert out of bounds
	 */
	@Test
	void insertOutOfBounds() {
		ArrayIndexedCollection arr = new ArrayIndexedCollection();
		
		assertThrows( IndexOutOfBoundsException.class, ()->{arr.insert(2, -1);});
	}
	
	/**
	 * check that indexOf returns -1
	 */
	@Test
	void checkIndexOf() {
		ArrayIndexedCollection arr = new ArrayIndexedCollection( 3);
		arr.add(0);
		arr.add(1);
		assertEquals(-1, arr.indexOf( 5));
	}
	/**
	 * check that indexOf returns -1 for null
	 */
	@Test
	void checkIndexOfNull() {
		ArrayIndexedCollection arr = new ArrayIndexedCollection( 3);
		arr.add(0);
		arr.add(1);
		assertEquals(-1, arr.indexOf( null));
	}
	/**
	 * check if indexOf return right values
	 */
	@Test
	void checkIndexOfReturn() {
		ArrayIndexedCollection arr = new ArrayIndexedCollection( 3);
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
		ArrayIndexedCollection arr = new ArrayIndexedCollection( 3);
		arr.add(1);
		assertThrows( IndexOutOfBoundsException.class, ()->{arr.remove( 3);});
	}
	
	/**
	 * check if remove IndexOutOfBounds
	 */
	@Test
	void removeOutOfBound2s() {
		ArrayIndexedCollection arr = new ArrayIndexedCollection( 3);
		arr.add(1);
		assertThrows( IndexOutOfBoundsException.class, ()->{arr.remove( -5);});
	}
	
	/**
	 * check if remove removes value
	 */
	@Test
	void removeValue() {
		ArrayIndexedCollection arr = new ArrayIndexedCollection( 3);
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
		ArrayIndexedCollection arr = new ArrayIndexedCollection();
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
		ArrayIndexedCollection arr = new ArrayIndexedCollection();
		arr.insert(0, 0);
		arr.insert(1, 0);
		
		assertEquals( 1, arr.indexOf( Integer.valueOf(0)));
		assertEquals( 0, arr.indexOf( Integer.valueOf(1)));
	}
	
	/**
	 * remove and then insert
	 */
	@Test
	void removeAndThenInsert() {
		ArrayIndexedCollection arr = new ArrayIndexedCollection();
		arr.add(1);
		arr.add(2);
		arr.add(3);
		
		arr.remove( Integer.valueOf( 2));
		arr.insert(2, 1);
		assertArrayEquals( new Object[] {Integer.valueOf( 1), Integer.valueOf(2), Integer.valueOf( 3)}, arr.toArray());
	}
}

