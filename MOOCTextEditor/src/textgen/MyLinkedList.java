package textgen;

import java.util.AbstractList;


/** A class that implements a doubly linked list
 * 
 * @author UC San Diego Intermediate Programming MOOC team
 *
 * @param <E> The type of the elements stored in the list
 */
public class MyLinkedList<E> extends AbstractList<E> {
	LLNode<E> head;
	LLNode<E> tail;
	int size;

	/** Create a new empty LinkedList */
	public MyLinkedList() {
		// TODO: Implement this method
		
		// use sentinel nodes as suggested by the instructor 
		// this helps to eliminate checks on things such as empty list ... 
		// essentially we will never get empty list
		this.head = new LLNode<E>(null);
		this.tail = new LLNode<E>(null);
		head.next = tail;
		tail.prev = head;
	}

	/**
	 * Appends an element to the end of the list
	 * @param element The element to add
	 */
	public boolean add(E element ) 
	{
		
		// TODO: Implement this method
		
		
		// null is not allowed
		// i don't know what to throw here
		if (element == null)
			throw new NullPointerException ();
		
		
		// just by creating a node, it takes care of inserting and reorganizing nodes
		new LLNode<E>(element,this.tail.prev, this.tail);
		
		this.size++;
		
		return true;
	}

	/** Get the element at position index 
	 * @throws IndexOutOfBoundsException if the index is out of bounds. */
	public E get(int index) 
	{
		// TODO: Implement this method.
		
		if(isOutofBound(index))
			throw new IndexOutOfBoundsException();
		
		// use of dummy node to make program more clean 
		LLNode<E> currentNode = this.head;
		
		for (int i = 0; i <= index; i++)
			currentNode = currentNode.next;

		
		return currentNode.data;
	}

	/**
	 * Add an element to the list at the specified index
	 * @param The index where the element should be added
	 * @param element The element to add
	 */
	public void add(int index, E element ) 
	{
		// TODO: Implement this method
		// the check is slightly different here
		// the index can be as big as the size - this would be to append to the end
		if(index < 0 || index > this.size)
			throw new IndexOutOfBoundsException();
		
		// null is not allowed
		if (element == null)
			throw new NullPointerException ();

		LLNode<E> currentNode = this.head;
		
		for (int i = 0; i <= index; i++)
			currentNode = currentNode.next;
			
		LLNode<E> frontNode = currentNode.prev;
		new LLNode<E>(element,frontNode,currentNode);	
		
		size++;
	}


	/** Return the size of the list */
	public int size() 
	{
		// TODO: Implement this method
		return this.size;
	}

	/** Remove a node at the specified index and return its data element.
	 * @param index The index of the element to remove
	 * @return The data element removed
	 * @throws IndexOutOfBoundsException If index is outside the bounds of the list
	 * 
	 */
	public E remove(int index) 
	{
		// TODO: Implement this method
		if(isOutofBound(index))
			throw new IndexOutOfBoundsException();
		
		LLNode<E> currentNode = this.head;
		
		for (int i = 0; i <= index; i++)
			currentNode = currentNode.next;
		
		currentNode.prev.next = currentNode.next;
		currentNode.next.prev = currentNode.prev;
		
		size--;
		
		return currentNode.data;
	}

	/**
	 * Set an index position in the list to a new element
	 * @param index The index of the element to change
	 * @param element The new element
	 * @return The element that was replaced
	 * @throws IndexOutOfBoundsException if the index is out of bounds.
	 */
	public E set(int index, E element) 
	{
		// TODO: Implement this method
		if(isOutofBound(index))
			throw new IndexOutOfBoundsException();
		
		// null is not allowed
		if (element == null)
			throw new NullPointerException ();	
		
		LLNode<E> currentNode = this.head;
		
		for (int i = 0; i <= index; i++)
			currentNode = currentNode.next;
		
		E replaced = currentNode.data;
		currentNode.data = element;
		
		return replaced;
	}  
	
	
	private boolean isOutofBound(int index){
		if(index < 0 || index > this.size - 1)
			return true;
		return false;
	}
	
}

class LLNode<E> 
{
	LLNode<E> prev;
	LLNode<E> next;
	E data;

	// TODO: Add any other methods you think are useful here
	// E.g. you might want to add another constructor

	public LLNode(E e) 
	{
		this.data = e;
		this.prev = null;
		this.next = null;
	}
	
	public LLNode(E e, LLNode<E> prev, LLNode<E> next){
		this.data = e;
		this.prev = prev;
		this.next = next;	
		
		this.prev.next = this;
		this.next.prev = this;
		
	}

}
