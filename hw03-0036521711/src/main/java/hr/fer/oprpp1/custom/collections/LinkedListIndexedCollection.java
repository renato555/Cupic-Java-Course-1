package hr.fer.oprpp1.custom.collections;

import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

/**
 * Extends Collection.
 * Linked list backed collection.
 * @author renat
 *
 */
public class LinkedListIndexedCollection<T> implements List<T>{
	/**
	 * Internal class for storing values and linking them together.
	 * @author renat
	 *
	 */
	private static class ListNode<T>{
		ListNode<T> previous;
		ListNode<T> next;
		T value;
		
		ListNode( ListNode<T> previous, ListNode<T> next, T value){
			this.previous = previous;
			this.next = next;
			this.value = value;
		}
		
		ListNode( T value){
			previous = next = null;
			this.value = value;
		}
		
	}
	/**
	 * Current size of the collection.
	 */
	private int size;
	/**
	 * Reference to the first node of the linked list. 
	 */
	private ListNode<T> first;
	/**
	 * Reference of the last node of the linked list.
	 */
	private ListNode<T> last;
	
	private long modificationCount = 0;
	
	/**
	 * Creates an empty collection.
	 */
	public LinkedListIndexedCollection() {
		first = last = null;
	}
	/**
	 * Creates a collection and fils it with elements from other collection.
	 * @param other contains elements that we want to add
	 */
	public LinkedListIndexedCollection( Collection<? extends T> other) {
		if( other == null) throw new NullPointerException( "other collection can not be null");
		addAll( other);
	}
	/**
	 * @throws NullPointerException if value is equal to null
	 */
	public void add( T value) {
		if( value == null) throw new NullPointerException( "value can not be null");
		
		if( size == 0) {
			ListNode<T> newNode = new ListNode<>( null, null, value);
			first = last = newNode;
		}else {
			ListNode<T> newNode = new ListNode<>( last, null, value); 
			last.next = newNode;
			last = newNode;
			
		}
		
		++modificationCount;
		++size;
	}
	/**
	 * @throws IndexOutOfBoundsException if <code>index < 0 || index > size-1</code
	 */
	public T get( int index) {
		if( index < 0 || index > size-1) throw new IndexOutOfBoundsException( "index can be bewteen 0 and size-1");
		
		return getListNode( index).value;
	}
	/**
	 * Returns ListNode at index <code>index</code>.
	 * @param index of the desired ListNode
	 * @return ListNode at index
	 * @throws IndexOutOfBoundsException if <code>index < 0 || index > size-1</code>
	 */
	private ListNode<T> getListNode( int index) {
		if( index < 0 || index > size-1) throw new IndexOutOfBoundsException( "index can be bewteen 0 and size-1");
		
		ListNode<T> temp;
		//if true start from first, else start from last
		if( index < size()/2) {
			temp = first;
			for( int i = 0; i < index; ++i) {
				temp = temp.next;
			}
		}else {
			temp = last;
			for( int i = size - 1; i > index; --i) {
				temp = temp.previous;
			}
		}
		
		return temp;
	}
	
	public void clear() {
		first = last = null;
		size = 0;
		++modificationCount;
	}
	/**
	 * @throws IndexOutOfBoundsException if <code>position < 0 || position > size</code>
	 * @throws NullPointerException if new element is null
	 */
	public void insert( T value, int position) {
		if( position < 0 || position > size) throw new IndexOutOfBoundsException( "position can be bewteen 0 and size");
		if( value == null) throw new NullPointerException( "value can not be null");
		
		if( size == 0 || position == size) {
			add( value);
		}else if( position == 0) {
			ListNode<T> newNode = new ListNode<>( null, first, value);
			first.previous = newNode;
			first = newNode;
			
			++size;
			++modificationCount;
		}else {
			ListNode<T> nodeAtPosition = getListNode( position);
			ListNode<T> prev = nodeAtPosition.previous;
			
			ListNode<T> newNode = new ListNode<>( prev, nodeAtPosition, value);
			prev.next = newNode;
			nodeAtPosition.previous = newNode;
			
			++size;
			++modificationCount;
		}
	}
	
	public int indexOf( Object value) {
		if( value == null) return -1;
		
		ListNode<T> curr = first;
		int index = 0;
		while( curr != null) {
			if( curr.value.equals(value)) {
				return index;
			}
			++index;
			curr = curr.next;
		}
		
		return -1;
	}
	/**
	 * @throws IndexOutOfBoundsException if <code>index < 0 || index >= size</code>
	 */
	public void remove( int index) {
		if( index < 0 || index >= size) throw new IndexOutOfBoundsException( "index can be bewteen 0 and size-1");
		
		if( size == 1) {
			clear();
		}else if( index == 0) {
			first = first.next;
			first.previous = null;
			
			--size;
			++modificationCount;
		}else if( index == size-1) {
			last = last.previous;
			last.next = null;
			
			--size;
			++modificationCount;
		}else{
			ListNode<T> temp = getListNode( index);
			temp.next.previous = temp.previous;
			temp.previous.next = temp.next;
			
			--size;
			++modificationCount;
		}
	}
	
	public boolean remove( Object value) {
		if( size == 0 || value == null) return false;
		
		//check if element is in the list
		int index = 0;
		ListNode<T> curr = first;
		while( curr != null) {
			if( curr.value.equals( value)) {
				break;
			}
			++index;
			curr = curr.next;
		}
		
		//element was not found
		if( curr == null) return false;
		
		//element was found, remove it
		if( index == 0 || index == size-1) {
			remove( index);
		}else{
			curr.next.previous = curr.previous;
			curr.previous.next = curr.next;
			
			--size;
			++modificationCount;
		}
		return true;
	}
	
	public int size() { 
		return size;
	}
	
	public boolean contains( Object value) {
		if( value == null) return false;
		
		ListNode<T> curr = first;
		while( curr != null) {
			if( curr.value.equals( value)) {
				return true;
			}
			curr = curr.next;
		}
		
		return false;
	}
	/**
	 * @throws UnsupportedOperationException if the collection does not contain any elements
	 */
	public Object[] toArray() {
		if( size < 1) {
			throw new UnsupportedOperationException( "Collection does not contain any elements.");
		}
		
		Object[] newArr = new Object[ size];
		ListNode<T> curr = first;
		int index = 0;
		while( curr != null) {
			newArr[index] = curr.value;
			
			++index;
			curr = curr.next;
		}
		
		return newArr;
	}
	
	/**
	 * Returns ElementsGetter for this collection.
	 */
	@Override
	public ElementsGetter<T> createElementsGetter() {
		return new ListElementsGetter<>( this);
	}
	
	/**
	 * Implementation for ElementsGetter for LinkedListIndexedCollection.
	 * @author renat
	 *
	 */
	private static class ListElementsGetter<T> implements ElementsGetter<T>{
		/**
		 * Reference to parent collection.
		 */
		private LinkedListIndexedCollection<T> list;
		/**
		 * A reference to current ListNode
		 */
		private ListNode<T> currNode;
		/**
		 * Number of modification to parent collection when ElementsGetter was created
		 */
		private long savedModificationCount;
		
		/**
		 * Default contructor.
		 * @param list parent collection
		 */
		private ListElementsGetter( LinkedListIndexedCollection<T> list) {
			this.list = list;
			currNode = list.first;
			savedModificationCount = list.modificationCount;
		}
		
		/**
		 * 
		 * @throws ConcurrentModificationException if parent collection has been modified since this element getter has been created 
		 */
		@Override
		public boolean hasNextElement() {
			if( savedModificationCount != list.modificationCount) throw new ConcurrentModificationException( "the collection has been changed");
			return currNode != null;
		}
		
		/**
		 * 
		 * @throws ConcurrentModificationException if parent collection has been modified since this element getter has been created 
		 * @throws NoSuchElementException if this method is called when there are no elements left
		 */
		@Override
		public T getNextElement() {
			if( savedModificationCount != list.modificationCount) throw new ConcurrentModificationException( "the collection has been changed"); //redundantno ali citljivije
			if( !hasNextElement()) throw new NoSuchElementException( "no more elements");
			
			T result = currNode.value;
			currNode = currNode.next;
			return result;
		}
		
	}
}
