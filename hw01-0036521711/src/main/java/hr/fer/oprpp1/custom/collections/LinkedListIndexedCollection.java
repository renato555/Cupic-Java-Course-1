package hr.fer.oprpp1.custom.collections;

/**
 * Extends Collection.
 * Linked list backed collection.
 * @author renat
 *
 */
public class LinkedListIndexedCollection extends Collection{
	/**
	 * Internal class for storing values and linking them together.
	 * @author renat
	 *
	 */
	private static class ListNode{
		ListNode previous;
		ListNode next;
		Object value;
		
		ListNode( ListNode previous, ListNode next, Object value){
			this.previous = previous;
			this.next = next;
			this.value = value;
		}
		
		ListNode( Object value){
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
	private ListNode first;
	/**
	 * Reference of the last node of the linked list.
	 */
	private ListNode last;
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
	public LinkedListIndexedCollection( Collection other) {
		if( other == null) throw new NullPointerException( "other collection can not be null");
		addAll( other);
	}
	/**
	 * @throws NullPointerException if value is equal to null
	 */
	public void add( Object value) {
		if( value == null) throw new NullPointerException( "value can not be null");
		
		if( size == 0) {
			ListNode newNode = new ListNode( null, null, value);
			first = last = newNode;
		}else {
			ListNode newNode = new ListNode( last, null, value); 
			last.next = newNode;
			last = newNode;
			
		}
		
		++size;
	}
	/**
	 * Returns element at index <code>index</code>.
	 * @param index of the desired element
	 * @return element at index
	 * @throws IndexOutOfBoundsException if <code>index < 0 || index > size-1</code>
	 */
	public Object get( int index) {
		if( index < 0 || index > size-1) throw new IndexOutOfBoundsException( "index can be bewteen 0 and size-1");
		
		return getListNode( index).value;
	}
	/**
	 * Returns ListNode at index <code>index</code>.
	 * @param index of the desired ListNode
	 * @return ListNode at index
	 * @throws IndexOutOfBoundsException if <code>index < 0 || index > size-1</code>
	 */
	private ListNode getListNode( int index) {
		if( index < 0 || index > size-1) throw new IndexOutOfBoundsException( "index can be bewteen 0 and size-1");
		
		ListNode temp;
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
		size = 0; 
		first = last = null;
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
		if( value == null) throw new NullPointerException( "value can not be null");
		
		if( size == 0 || position == size) {
			add( value);
		}else if( position == 0) {
			ListNode newNode = new ListNode( null, first, value);
			first.previous = newNode;
			first = newNode;
			
			++size;
		}else {
			ListNode nodeAtPosition = getListNode( position);
			ListNode prev = nodeAtPosition.previous;
			
			ListNode newNode = new ListNode( prev, nodeAtPosition, value);
			prev.next = newNode;
			nodeAtPosition.previous = newNode;
			
			++size;
		}
	}
	/**
	 * Returns the index of the first occurrence of the given value.
	 * Equality is determined by using equals method.
	 * @param value for which we wish to get the index
	 * @return index of the input value or -1 if it is not found
	 */
	public int indexOf( Object value) {
		if( value == null) return -1;
		
		ListNode curr = first;
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
	 * Removes an element at index and shifts elements that where at greater index one
	 * spot to the left.
	 * @param index of element that need to be removed
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
		}else if( index == size-1) {
			last = last.previous;
			last.next = null;
			
			--size;
		}else{
			ListNode temp = getListNode( index);
			temp.next.previous = temp.previous;
			temp.previous.next = temp.next;
			
			--size;
		}
	}
	
	public boolean remove( Object value) {
		if( size == 0 || value == null) return false;
		
		//check if element is in the list
		int index = 0;
		ListNode curr = first;
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
		}
		return true;
	}
	
	public int size() { 
		return size;
	}
	
	public boolean contains( Object value) {
		if( value == null) return false;
		
		ListNode curr = first;
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
			super.toArray();
		}
		
		Object[] newArr = new Object[ size];
		ListNode curr = first;
		int index = 0;
		while( curr != null) {
			newArr[index] = curr.value;
			
			++index;
			curr = curr.next;
		}
		
		return newArr;
	}
	
	public void forEach( Processor processor) {
		ListNode curr = first;
		while( curr != null) {
			processor.process( curr.value);
			curr = curr.next;
		}
	}
}
