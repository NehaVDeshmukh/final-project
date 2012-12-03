package student;

import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class RingBuffer<E> implements BlockingQueue<E>{

	int index1; // head index
	int index2; // tail index
	int capacity;
	int size; 
	E[] rbuf;
	Object full = new Object();
	Object empty = new Object();
	
	public RingBuffer(int cap)
	{
		rbuf = (E[]) new Object[cap];
		capacity = cap;
	}
		
	private int increment(int i)
	{
		return (i+1)%capacity;
	}
	/**(Queue)Retrieves, but does not remove, the head of this queue.
	 * throws NoSuchElementException if empty
	 * */
	@Override 
	public synchronized E element() {
		if(size!=0)
			return rbuf[index1];
		else throw new NoSuchElementException();
	}

	/**(Queue)Retrieves, but does not remove, 
	 * the head of this queue, returning null if this queue is empty.
	 * */
	@Override
	public synchronized E peek() {
		if(size!=0)
			return rbuf[index1];
		else return null;
	}

	/**(Queue)Retrieves and removes the head of this queue, 
	 * or null if this queue is empty.
	 * */
	@Override
	public E poll() {
		synchronized(full)
		{
			if(size!=0)
			{
				E e = rbuf[index1];
				rbuf[index1] = null;
				index1 = increment(index1);
				size--;
				full.notifyAll();
				return e;
			}
			else 
			{
				return null;			
			
			}
		}
	}

	/**(Queue)Retrieves and removes the head of this queue.
	 * throws NoSuchElementException if empty
	 */
	@Override
	public synchronized E remove() {
		synchronized(full)
		{
			if(size!=0)
			{
				E e = rbuf[index1];
				rbuf[index1] = null;
				index1 = increment(index1);
				size--;
				notifyAll();
				return e;
			}
			else throw new NoSuchElementException();			
		}
	}

	@Override
	public boolean addAll(Collection<? extends E> arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void clear() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean containsAll(Collection<?> arg0) {
		throw new UnsupportedOperationException();
	}

	/**(Collection)Returns true if this collection contains no elements.*/
	@Override
	public synchronized boolean isEmpty() {
		return size==0;
	}

	/**(Collection)Returns an iterator over the elements in this collection.*/
	@Override
	public synchronized Iterator<E> iterator() {
		return new RBIterator<E>(rbuf, index1,index2);		
	}

	@Override
	public boolean removeAll(Collection<?> arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean retainAll(Collection<?> arg0) {
		throw new UnsupportedOperationException();
	}

	/**(Collection)Returns the number of elements in this collection.*/
	@Override
	public synchronized int size() {
		return size;
	}

	@Override
	public Object[] toArray() {
		throw new UnsupportedOperationException();
	}

	/**(Collection)Compares the specified object with this collection for equality.*/
	@Override
	public synchronized boolean equals(Object arg0) {
		RingBuffer<E> rb = (RingBuffer)arg0;
		if(capacity == rb.capacity)
		{
			for(int i=0;i<capacity;i++)
			{
				int tmp=increment(index1+i-1);
				int tmp2=increment(rb.index1+i-1);
				if(!rbuf[tmp].equals(rb.rbuf[tmp2]))
				{
					return false;
				}
			}
			return true;
		}
		else return false;
	}

	@Override
	public <T> T[] toArray(T[] arg0) {
		throw new UnsupportedOperationException();
	}

	/**(BlockingQueue)Inserts the specified element into this queue 
	 * if it is possible to do so immediately without violating capacity restrictions, 
	 * returning true upon success and throwing an IllegalStateException if no space is currently available.
	 * throws IllegalStateException - if the element cannot be added at this time due to capacity restrictions
	 * ClassCastException - if the class of the specified element prevents it from being added to this queue
	 * NullPointerException - if the specified element is null
	 * IllegalArgumentException - if some property of the specified element prevents it from being added to this queue
	 * */
	@Override
	public boolean add(E arg0) {
		synchronized(empty)
		{
			if(size == capacity)
			{
				throw new IllegalStateException();
			}
			else if(arg0==null)
				throw new NullPointerException();
			else if(size!=0 && !rbuf[index1].getClass().equals(arg0.getClass()))
				throw new ClassCastException();
//			else if(getClass().getGenericSuperclass().getActualTypeArguments())
			else
			{
				try
				{
					rbuf[index2] = arg0;
					index2 = increment(index2);
					size++;
					empty.notifyAll();
					return true;				
				}
				catch(Exception ex)
				{
					throw new IllegalArgumentException();
				}
			}			
		}

	}

	/**(BlockingQueue)Returns true if this queue contains the specified element.
	 * throws ClassCastException - if the class of the specified element is incompatible with this queue (optional)
	 * NullPointerException - if the specified element is null (optional)
	 * */
	@Override
	public synchronized boolean contains(Object arg0) {
		if(arg0 == null)
		{
			throw new NullPointerException();
		}
		if(size!=0 && !(rbuf[index1].getClass().equals(arg0.getClass())))
			throw new ClassCastException();
		for(E e:rbuf)
		{
			if(e!=null && e.equals(arg0))
				return true;
		}
		return false;
	}

	/**(BlockingQueue)Removes all available elements 
	 * from this queue and adds them to the given collection.
	 * throws UnsupportedOperationException - if addition of elements is not supported by the specified collection
	 * NullPointerException - if the specified collection is null
	 * ClassCastException - if the class of the specified element prevents it from being added to this queue
	 * IllegalArgumentException - if the specified collection is this queue, 
	 * or some property of an element of this queue prevents it from being added to the specified collection
	 * */
	@Override
	public synchronized int drainTo(Collection<? super E> arg0) {
		int success=0;
		if(arg0 == null)
			throw new NullPointerException();
		for(int i=0;i<size;i++)
		{
			E e = poll();
			try
			{
				arg0.add(e);				
				success++;
			}
			catch(Exception ex)
			{
				throw new IllegalArgumentException();
			}
		}
		return success;
	}

	/**(BlockingQueue)Removes at most the given number of available elements 
	 * from this queue and adds them to the given collection
	 * throws UnsupportedOperationException - if addition of elements is not supported by the specified collection
	 * ClassCastException - if the class of an element of this queue prevents it from being added to the specified collection
	 * NullPointerException - if the specified collection is null
	 * IllegalArgumentException - if the specified collection is this queue, 
	 * or some property of an element of this queue prevents it from being added to the specified collection
	 * */
	@Override
	public synchronized int drainTo(Collection<? super E> arg0, int arg1) {
		int success=0;
		if(arg0 == null)
			throw new NullPointerException();
		for(int i=0;i<arg1;i++)
		{
			E e = poll();
			try
			{
				arg0.add(e);				
				success++;
			}
			catch(Exception ex)
			{
				throw new IllegalArgumentException();
			}
		}
		return success;
	}

	/**(Queue)Inserts the specified element into this queue, if possible.
	 * (BlockingQueue)Inserts the specified element into this queue 
	 * if it is possible to do so immediately without violating capacity restrictions, 
	 * returning true upon success and false if no space is currently available.
	 * throws IllegalStateException - if the element cannot be added at this time due to capacity restrictions
	 * ClassCastException - if the class of the specified element prevents it from being added to this queue
	 * NullPointerException - if the specified element is null
	 * IllegalArgumentException - if some property of the specified element prevents it from being added to this queue
	 * */
	@Override
	public synchronized boolean offer(E arg0) {
		if(arg0 == null)
		{
			throw new NullPointerException();
		}
		else if(size==capacity)
		{
			throw new IllegalStateException();
		}
		else if(size!=0 && !(rbuf[index1].getClass().equals(arg0.getClass())))
		{
			throw new ClassCastException();
		}
		else
		{
			try
			{
				rbuf[index2] = arg0;
				index2++;
				size++;
				return true;				
			}
			catch(Exception ex)
			{
				throw new IllegalArgumentException();
			}
		}
	}

	/**(BlockingQueue)Inserts the specified element into this queue, 
	 * waiting up to the specified wait time 
	 * if necessary for space to become available.
	 * parameters timeout - how long to wait before giving up, in units of unit
	 * unit - a TimeUnit determining how to interpret the timeout parameter
	 * */
	@Override
	public synchronized boolean offer(E arg0, long timeout, TimeUnit unit)
			throws InterruptedException {
		if(arg0 == null)
		{
			throw new NullPointerException();
		}
		else if(size==capacity)
		{
			throw new IllegalStateException();
		}
		else if(size!=0 && !(rbuf[index1].getClass().equals(arg0.getClass())))
		{
			throw new ClassCastException();
		}
		else
		{
			synchronized(full)
			{
			    long nanos = unit.toMillis(timeout);
			    if(size == capacity)
			    {
			    	full.wait(nanos);
			    }				
				try
				{
					rbuf[index2] = arg0;
					index2++;
					size++;
					return true;				
				}
				catch(Exception ex)
				{
					throw new IllegalArgumentException();
				}
			}
		}
	}

	/**(BlockingQueue)Retrieves and removes the head of this queue, 
	 * waiting up to the specified wait time 
	 * if necessary for an element to become available.
	 * throws InterruptedException - if interrupted while waiting
	 * */
	@Override
	public E poll(long timeout, TimeUnit unit) throws InterruptedException {
		synchronized(empty)
		{
			long nanos = unit.toMillis(timeout);
			if(size == 0)
			{
				empty.wait(nanos);
			}				
			try
			{
				return remove();
			}
			catch(Exception ex)
			{
				throw new IllegalArgumentException();
			}
		}
	}

	/**(BlockingQueue)Inserts the specified element into this queue, 
	 * waiting if necessary for space to become available.*/
	@Override
	public synchronized void put(E arg0) throws InterruptedException {
		if(arg0 == null)
		{
			throw new NullPointerException();
		}
		else if(size==capacity)
		{
			throw new IllegalStateException();
		}
		else if(size!=0 && !(rbuf[index1].getClass().equals(arg0.getClass())))
		{
			throw new ClassCastException();
		}
		else
		{
			synchronized(full)
			{
			    if(size == capacity)
			    {
			    	full.wait();
			    }				
				try
				{
					rbuf[index2] = arg0;
					index2++;
					size++;
				}
				catch(Exception ex)
				{
					throw new IllegalArgumentException();
				}
			}
		}		
	}

	/**(BlockingQueue)Returns the number of additional elements that this queue can ideally 
	 * (in the absence of memory or resource constraints) accept without blocking, 
	 * or Integer.MAX_VALUE if there is no intrinsic limit.*/
	@Override
	public synchronized int remainingCapacity() {
		return capacity - size;
	}

	/**(BlockingQueue)Removes a single instance of the specified element from this queue, 
	 * if it is present.
	 * throws ClassCastException - if the class of the specified element is incompatible with this queue (optional)
	 * NullPointerException - if the specified element is null (optional)
	 * */
	@Override
	public synchronized boolean remove(Object arg0) {
		int itemIndex=-1;
		int tsize=0;
		if(arg0 == null)
			throw new NullPointerException();
		else if(size!=0 && !rbuf[index1].getClass().equals(arg0.getClass()))
			throw new ClassCastException();
		else
		{
			if(contains(arg0))
			{
				for(int i=0;i<size;i++)
				{
					int tmp=increment(index1+i-1);
					if(rbuf[tmp].equals(arg0))
					{
						itemIndex = tmp;
						break;
					}
					tsize++;					
				}
				for(int i=0;i<size-tsize;i++)
				{
					int tmp = increment(itemIndex+i-1);
					int tmp2 = increment(itemIndex+i);
					rbuf[tmp] = rbuf[tmp2];
				}
				rbuf[index2-1]=null;
				index2 = index2-1>=0?index2-1:capacity-1;
				return true;
			}
			else return false;
		}
			

	}

	/**(BlockingQueue)Retrieves and removes the head of this queue, 
	 * waiting if necessary until an element becomes available.*/
	@Override
	public E take() throws InterruptedException {
		synchronized(empty)
		{
			if(size == 0)
			{
				empty.wait();
			}				
			try
			{
				return remove();
			}
			catch(Exception ex)
			{
				throw new IllegalArgumentException();
			}
		}
	}
	private class RBIterator<E> implements Iterator<E>{
		private E cur;
		private E next;
		int curIndex=-1;

		public RBIterator(E[] rbuf, int index1, int index2){
			cur = rbuf[index1];
			curIndex = index1-1;
		}

		private int increment(int i)
		{
			return (i+1)%capacity;
		}

		private boolean isEmpty()
		{
			boolean empty = true;
			for(int i=0;i<rbuf.length;i++)
			{
				if(rbuf[i]!=null)
				{
					empty = false;
				}
			}			
			return empty;
		}
		private boolean isFull()
		{
			boolean full = true;
			for(int i=0;i<rbuf.length;i++)
			{
				if(rbuf[i]==null)
				{
					full = false;
				}
			}			
			return full;
		}
		/** Returns true if the iteration has more elements.
		 * */
		@Override
		public boolean hasNext() {
			if(isEmpty())
			{
				return false;
			}
			if(isFull()&&increment(curIndex)==index2) 
				return false;
			else return rbuf[increment(curIndex)]!=null;
		}

		/**Returns the next element in the iteration.
		 * NoSuchElementException - iteration has no more elements.
		 * */
		@SuppressWarnings("unchecked")
		@Override
		public E next() {
			if(hasNext())
			{
				next = (E) rbuf[increment(curIndex)];
				curIndex = increment(curIndex);
				return next;
			}
			else throw new NoSuchElementException();
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}
	}		
}
