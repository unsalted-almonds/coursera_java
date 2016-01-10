/**
 * 
 */
package textgen;

import static org.junit.Assert.*;

import java.util.LinkedList;

import org.junit.Before;
import org.junit.Test;

/**
 * @author UC San Diego MOOC team
 *
 */
public class MyLinkedListTester {

	private static final int LONG_LIST_LENGTH =10; 

	MyLinkedList<String> shortList;
	MyLinkedList<Integer> emptyList;
	MyLinkedList<Integer> longerList;
	MyLinkedList<Integer> list1;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		// Feel free to use these lists, or add your own
	    shortList = new MyLinkedList<String>();
		shortList.add("A");
		shortList.add("B");
		emptyList = new MyLinkedList<Integer>();
		longerList = new MyLinkedList<Integer>();
		for (int i = 0; i < LONG_LIST_LENGTH; i++)
		{
			longerList.add(i);
		}
		list1 = new MyLinkedList<Integer>();
		list1.add(65);
		list1.add(21);
		list1.add(42);
		
	}

	
	/** Test if the get method is working correctly.
	 */
	/*You should not need to add much to this method.
	 * We provide it as an example of a thorough test. */
	@Test
	public void testGet()
	{
		//test empty list, get should throw an exception
		try {
			emptyList.get(0);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
			
		}
		
		// test short list, first contents, then out of bounds
		assertEquals("Check first", "A", shortList.get(0));
		assertEquals("Check second", "B", shortList.get(1));
		
		try {
			shortList.get(-1);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
		
		}
		try {
			shortList.get(2);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
		
		}
		// test longer list contents
		for(int i = 0; i<LONG_LIST_LENGTH; i++ ) {
			assertEquals("Check "+i+ " element", (Integer)i, longerList.get(i));
		}
		
		// test off the end of the longer array
		try {
			longerList.get(-1);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
		
		}
		try {
			longerList.get(LONG_LIST_LENGTH);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
		}
		
	}
	
	
	/** Test removing an element from the list.
	 * We've included the example from the concept challenge.
	 * You will want to add more tests.  */
	@Test
	public void testRemove()
	{
		int a = list1.remove(0);
		assertEquals("Remove: check a is correct ", 65, a);
		assertEquals("Remove: check element 0 is correct ", (Integer)21, list1.get(0));
		assertEquals("Remove: check size is correct ", 2, list1.size());
		
		// TODO: Add more tests here
		try {
			list1.remove(-1);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
		}		
		try {
			list1.remove(3);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
		}			
	}
	
	/** Test adding an element into the end of the list, specifically
	 *  public boolean add(E element)
	 * */
	@Test
	public void testAddEnd()
	{
        // TODO: implement this test
		//assertEquals("Check empty add null", false, emptyList.add(null));
		assertEquals("Check empty add non-null", true, emptyList.add((Integer)123));
		assertEquals("Check empty add non-null", true, emptyList.add((Integer)456));
		
		//assertEquals("Check short add null", false, shortList.add(null));
		assertEquals("Check short add non-null", true, shortList.add("abc"));

		//assertEquals("Check long add null", false, longerList.add(null));
		assertEquals("Check long add non-null", true, longerList.add((Integer)123));
		
		assertEquals("Check long list", 11, longerList.size());			

		try {
			shortList.add(null);
			fail("Check null");
		}
		catch (NullPointerException e) {
		
		}	
		
	}

	
	/** Test the size of the list */
	@Test
	public void testSize()
	{
		// TODO: implement this test
		assertEquals("Check empty list", 0, emptyList.size());		
		assertEquals("Check short list", 2, shortList.size());		
		assertEquals("Check long list", 10, longerList.size());		
	}

	
	
	/** Test adding an element into the list at a specified index,
	 * specifically:
	 * public void add(int index, E element)
	 * */
	@Test
	public void testAddAtIndex()
	{
        // TODO: implement this test
		try {
			emptyList.add(1, 1);
			fail("Check empty out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
		
		}

		try {
			emptyList.add(-1, 1);
			fail("Check empty out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
		
		}
		
		try {
			shortList.add(3, "abc");
			fail("Check short out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
		
		}

		try {
			shortList.add(0, null);
			fail("Check null");
		}
		catch (NullPointerException e) {
		
		}	
		
		shortList.add(2, "abc");
		assertEquals("AddAtIndex: check element 2 is correct ", "abc", shortList.get(2));			
		assertEquals("AddAtIndex: check size is correct ", 3, shortList.size());		
			
	}
	
	/** Test setting an element in the list */
	@Test
	public void testSet()
	{
	    // TODO: implement this test
		try {
			emptyList.set(0, 1);
			fail("Check empty out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
		
		}

		try {
			shortList.set(11, "abc");
			fail("Check short out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
		
		}	
		
		try {
			shortList.set(-1, "abc");
			fail("Check short out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
		
		}	
		
		try {
			shortList.set(0, null);
			fail("Check null");
		}
		catch (NullPointerException e) {
		
		}	
		
		shortList.set(1, "this is a test");
		assertEquals("Set: check element 1 is correct ", "this is a test", shortList.get(1));			
	}
	
	
	// TODO: Optionally add more test methods.
	
}
