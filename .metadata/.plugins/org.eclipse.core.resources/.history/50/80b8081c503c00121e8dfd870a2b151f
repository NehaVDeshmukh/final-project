package student;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

@SuppressWarnings("javadoc")
public class LList<T> implements List<T> {
	private Node<T> head;
	private int length;

	/**
	 * Creates a new LList
	 * 
	 * @param head
	 *            the first element in the list, null if creating an empty list
	 */
	public LList(Node<T> head) {
		this.head = head;
		if (head != null)
			length = 1;
		else
			length = 0;
	}

	@Override
	public boolean add(T e) {
		Node<T> n = new Node<T>(e, head);
		head = n;
		length++;
		return true;
	}

	@Override
	public void add(int index, T element) {
		Node<T> n = head;
		if (index < length)
			for (int i = 0; i < index; i++)
				n = n.getNext();

		Node<T> added = new Node<T>(element, n.getNext());
		n.setNext(added);
		length++;
	}

	@Override
	public boolean addAll(Collection<? extends T> c) {
		boolean r = true;
		for (T e : c)
			r = r & add(e);

		return r;
	}

	@Override
	public boolean addAll(int index, Collection<? extends T> c) {
		for (T e : c)
			add(index, e);

		return true;
	}

	@Override
	public void clear() {
		head = null;
		length = 0;
	}

	@Override
	public boolean contains(Object o) {
		return (indexOf(o) > -1);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public T get(int index) {
		Node<T> n = head;
		for (int i = 0; i < index; i++)
			n = n.getNext();

		return n.getData();
	}

	@Override
	public int indexOf(Object o) {
		Node<T> n = head;
		int i = 0;
		while (n != null) {
			if (n.getData().equals(o))
				return i;
			n = n.getNext();
			i++;
		}
		return -1;
	}

	@Override
	public boolean isEmpty() {
		return (length == 0);
	}

	@Override
	public Iterator<T> iterator() {
		return new LIterator<T>(this.head);
	}

	@Override
	public int lastIndexOf(Object o) {
		throw new UnsupportedOperationException();
	}

	@Override
	public ListIterator<T> listIterator() {
		throw new UnsupportedOperationException();
	}

	@Override
	public ListIterator<T> listIterator(int index) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean remove(Object o) {
		Node<T> n = head;
		while (n != null && n.getNext() != null) {
			if (n.getNext().getData().equals(o)) {
				n.setNext(n.getNext().getNext());
				return true;
			}
			n = n.getNext();
		}
		return false;
	}

	@Override
	public T remove(int index) {
		if (index >= length)
			throw new IndexOutOfBoundsException();

		T data = null;
		if (index == 0) {
			data = head.getData();
			head = head.getNext();
		} else {
			Node<T> n = head;
			for (int i = 1; i < index; i++)
				n = n.getNext();

			data = n.getNext().getData();
			n.setNext(n.getNext().getNext());
		}

		length--;

		return data;
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public T set(int index, T element) {
		throw new UnsupportedOperationException();
	}

	@Override
	public int size() {
		return length;
	}

	@Override
	public List<T> subList(int fromIndex, int toIndex) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Object[] toArray() {
		throw new UnsupportedOperationException();
	}

	@Override
	public <E> E[] toArray(E[] a) {
		throw new UnsupportedOperationException();
	}

	private class LIterator<E> implements Iterator<E>{
		private Node<E> cur;
		
		public LIterator(Node<E> head){
			cur = head;
		}
		
		@Override
		public boolean hasNext() {
			return (cur!=null);
		}

		@Override
		public E next() {
			Node<E> next = cur;
			cur = cur.getNext();
			return next.getData();
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}
		
	}
}