package student;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Iterator;

import org.junit.Before;
import org.junit.Test;

import student.util.RingBufferFactory;

public class RingBufferTest {

	RingBuffer<Integer> rb;
	@Before
	public void setUp() throws Exception {
		RingBufferFactory<Integer> rbf = new RingBufferFactory<Integer>();
		rb = (RingBuffer<Integer>) rbf.getSynchronizedBuffer(5);
	}

	@Test
	public void elementTest1() //element test when empty
	{
		assertTrue(1 == rb.element()); // should throw NoSuchElementException if empty		
	}
	@Test
	public void peekTest1()//peek test when empty
	{
		assertNull(rb.peek()); // should return null if this queue is empty.		
	}
	@Test
	public void elementTest2() // element test when not empty
	{
		rb.add(1);
		rb.add(2);
		assertTrue(1 == rb.element());
		assertTrue(1 == rb.peek());		
	}
	@Test
	public void peekTest2() //peek test when not empty
	{
		rb.add(1);
		rb.add(2);
		assertTrue(1 == rb.peek());		
	}
	@Test
	public void test1() // poll and remove test when not empty
	{
		rb.add(1);
		rb.add(2);
		assertTrue(1==rb.poll());
		assertTrue(2==rb.remove());
	}
	@Test
	public void test2() // remove test when empty
	{
		assertTrue(1 == rb.remove()); // should throw NoSuchElementException if empty		
	}
	@Test
	public void pollTest1()//poll test, should return null when empty
	{
		assertNull(rb.poll());
	}
	@Test
	public void isEmptyTest() //isEmptyTest
	{
		assertTrue(rb.isEmpty());
		rb.add(1);
		assertTrue(!rb.isEmpty());
	}
	@Test
	public void sizeTest() // size test
	{
		assertTrue(rb.size==0);
		rb.add(1);
		assertTrue(rb.size==1);
		rb.add(2);
		rb.add(3);
		rb.add(4);
		rb.add(5);
		assertTrue(rb.size==5);
	}
	@Test
	public void iteratorTest() //iterator test
	{
		rb.add(1);
		rb.add(2);
		rb.add(3);
		rb.add(4);
		Iterator<Integer> itr = rb.iterator();
		System.out.println();
		while(itr.hasNext()) {
	         Object element = itr.next();
	         System.out.print(element + " ");
	      }
		System.out.println();
	}
	@Test
	public void equalsTest() // equals, two ring buffers that start at different index
	{
		RingBufferFactory<Integer> rbf = new RingBufferFactory<Integer>();
		RingBuffer<Integer> rb2 = (RingBuffer<Integer>) rbf.getSynchronizedBuffer(5);
		rb2.add(0);
		rb2.add(1);
		rb2.add(2);
		rb2.add(3);
		rb2.add(4);
		rb2.remove();
		rb2.add(5);
		rb.add(1);
		rb.add(2);
		rb.add(3);
		rb.add(4);
		rb.add(5);
		assertTrue(rb.equals(rb2));
	}
	@Test 
	public void equalsTest2() //equals, more general case of two ring buffers that start at same index
	{
		RingBufferFactory<Integer> rbf = new RingBufferFactory<Integer>();
		RingBuffer<Integer> rb2 = (RingBuffer<Integer>) rbf.getSynchronizedBuffer(5);
		rb2.add(1);
		rb2.add(2);
		rb2.add(3);
		rb2.add(4);
		rb2.add(5);
		rb.add(1);
		rb.add(2);
		rb.add(3);
		rb.add(4);
		rb.add(5);
		assertTrue(rb.equals(rb2));
	}
	@Test
	public void addTest1() // throws IllegalStateException - if the element cannot be added at this time due to capacity restrictions
	{
		rb.add(1);
		rb.add(2);
		rb.add(3);
		rb.add(4);
		rb.add(5);
		rb.add(6);
	}
	@Test
	public void addTest2() //throws ClassCastException - if the class of the specified element prevents it from being added to this queue
	{
		rb.add(1);
//		rb.add("two");		but caught at compile time
	}
	@Test
	public void addTest3() //throws NullPointerException - if the specified element is null
	{
		rb.add(null);		
	}
	@Test 
	public void addTest4() // general case
	{
		rb.add(1);
		rb.add(2);
		rb.add(3);
		rb.add(4);
		rb.add(5);		
	}
	@Test
	public void containsTest1() //NullPointerException - if the specified element is null (optional)
	{
		rb.contains(null);
	}
	@Test
	public void containsTest2() // general case 1, when contained
	{
		rb.add(1);
		rb.add(2);
		rb.add(3);
		assertTrue(rb.contains(1));
	}
	@Test
	public void containsTest3() // general case 1, when contained
	{
		rb.add(1);
		rb.add(2);
		rb.add(3);
		assertTrue(!rb.contains(4));
	}
	@Test
	public void drainTest1()
	{
		ArrayList<Integer> arr = new ArrayList<Integer>();
		rb.add(1);
		rb.add(2);
		rb.add(3);
		rb.drainTo(arr);
		Iterator<Integer> itr = arr.iterator();
		while(itr.hasNext()) {
	         Object element = itr.next();
	         System.out.print(element + " ");
	      }
		System.out.println();
	}
	@Test
	public void drainTest2()
	{
		ArrayList<Integer> arr = new ArrayList<Integer>();
		rb.add(1);
		rb.add(2);
		rb.add(3);
		rb.add(4);
		rb.add(5);
		rb.drainTo(arr,3);
		Iterator<Integer> itr = arr.iterator();
		while(itr.hasNext()) {
	         Object element = itr.next();
	         System.out.print(element + " ");
	      }
	}
	@Test
	public void offerTest()
	{
		assertTrue(rb.offer(1));
	}
	@Test
	public void offerTest2() //IllegalStateException - if the element cannot be added at this time due to capacity restrictions
	{
		rb.offer(1);
		rb.offer(2);
		rb.offer(3);
		rb.offer(4);
		rb.offer(5);
		assertTrue(rb.offer(6));
	}
	@Test
	public void offerTest3() //NullPointerException - if the specified element is null
	{
		assertTrue(rb.offer(null));
	}
	@Test
	public void capTest()
	{
		assertTrue(rb.remainingCapacity()==5);
		rb.add(1);
		rb.add(2);
		rb.add(3);
		assertTrue(rb.remainingCapacity()==2);
		rb.add(4);
		rb.add(5);
		assertTrue(rb.remainingCapacity()==0);
	}
}