package student;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.lang.UnsupportedOperationException;

/**
 * @author Laura Herrle
 *
 * @param <K>
 * @param <V>
 */
public class HashTable<K, V> implements java.util.Map<K, V> {

	// when a > 2, quadruple the size! this keeps 1/2 < a < 2 otherwise, double
	// when a > 1.5
	private int size;
	private Set<K> keys;
	private LList<HashEntry<K, V>>[] elements;

	/**
	 * @param s
	 *            the initial number of buckets
	 */
	@SuppressWarnings("unchecked")
	public HashTable(int s) {
		size = 0;
		elements = (LList<HashEntry<K, V>>[]) new LList[s];
		for (int i = 0; i < s; i++)
			elements[i] = new LList<HashEntry<K, V>>(null);

		keys = new KeySet<K>();
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean isEmpty() {
		return (size == 0);
	}

	@Override
	public boolean containsKey(Object key) {
		int i = this.hash(key);
		if (elements[i].isEmpty())
			return false;

		Iterator<HashEntry<K, V>> l = elements[i].iterator();
		HashEntry<K, V> h;

		while (l.hasNext()) {
			h = l.next();
			if (h.getKey().equals(key))
				return true;
		}

		return false;
	}

	@Override
	public boolean containsValue(Object value) {
		for (int i = 0; i < elements.length; i++) {
			if (!elements[i].isEmpty()) {
				Iterator<HashEntry<K, V>> l = elements[i].iterator();
				HashEntry<K, V> h;

				while (l.hasNext()) {
					h = l.next();
					if (h.getValue().equals(value))
						return true;
				}
			}
		}

		return false;
	}

	@Override
	public V get(Object key) {
		int i = hash(key);
		if (elements[i].isEmpty())
			return null;

		Iterator<HashEntry<K, V>> l = elements[i].iterator();
		HashEntry<K, V> h;

		while (l.hasNext()) {
			h = l.next();
			if (h.getKey().equals(key))
				return h.getValue();
		}

		return null;
	}

	@Override
	public V put(K key, V value) {
		V r = this.remove(key);

		int i = hash(key);
		elements[i].add(new HashEntry<K, V>(key, value));

		if(size/elements.length > 1.5)	//possibly change TODO to make always prime
			resize();

		return r;
	}

	@Override
	public V remove(Object key) {
		if (!this.containsKey(key))
			return null;

		int i = hash(key);

		Iterator<HashEntry<K, V>> l = elements[i].iterator();
		HashEntry<K, V> h;

		while (l.hasNext()) {
			h = l.next();
			if (h.getKey().equals(key)) {
				V v = h.getValue();
				elements[i].remove(h);
				return v;
			}
		}

		return null;
	}

	@Override
	public void putAll(Map<? extends K, ? extends V> m) {
		Set<? extends K> k = m.keySet();

		for (K key : k) {
			this.put(key, m.get(key));
		}
	}

	@Override
	public void clear() {
		keys.clear();
		for (int i = 0; i < elements.length; i++)
			elements[i].clear();
	}

	@Override
	public Set<K> keySet() {
		return keys;
	}

	private int hash(Object key) {
		int i = key.hashCode();
		if (i<0)
			i = -i;
		return (i % elements.length);
	}
	
	@SuppressWarnings("unchecked")
	private void resize()
	{
		LList<HashEntry<K, V>>[] newList =  (LList<HashEntry<K, V>>[]) new LList[elements.length*2];
		for (int i = 0; i < newList.length; i++)
			newList[i] = new LList<HashEntry<K, V>>(null);
		
		for (int i = 0; i < elements.length; i++) {
			if (!elements[i].isEmpty()) {
				Iterator<HashEntry<K, V>> l = elements[i].iterator();
				HashEntry<K, V> h;

				while (l.hasNext()) {
					h = l.next();
					newList[hash(h.getKey())].add(h);
				}
			}
		}
		
		elements = newList;
	}

	private class HashEntry<Y extends K, E extends V> implements
			java.util.Map.Entry<Y, E> {
		private Y key;
		private E value;

		public HashEntry(Y k, E v) {
			key = k;
			value = v;
		}

		@Override
		public Y getKey() {
			return key;
		}

		@Override
		public E getValue() {
			return value;
		}

		@Override
		public E setValue(E value) {
			E old = this.value;
			this.value = value;
			return old;
		}
	}

	private class KeySet<Y extends K> implements java.util.Set<Y> {
		private LList<Y> keys;
		private int size;

		public KeySet() {
			keys = new LList<Y>(null);
			size = 0;
		}

		@Override
		public boolean add(Y e) {
			if (keys.contains(e))
				return false;

			keys.add(e);
			return true;
		}

		@Override
		public boolean addAll(Collection<? extends Y> c) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void clear() {
			keys.clear();
			size = 0;
		}

		@Override
		public boolean contains(Object o) {
			return keys.contains(o);
		}

		@Override
		public boolean containsAll(Collection<?> c) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean isEmpty() {
			return (size == 0);
		}

		@Override
		public Iterator<Y> iterator() {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean remove(Object o) {
			return keys.remove(o);
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
		public int size() {
			return size;
		}

		@Override
		public Object[] toArray() {
			throw new UnsupportedOperationException();
		}

		@Override
		public <T> T[] toArray(T[] a) {
			throw new UnsupportedOperationException();
		}

	}

	/**
	 * It is not required that you implement the values() or entrySet()
	 * operations
	 **/
	@Override
	public Collection<V> values() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Set<java.util.Map.Entry<K, V>> entrySet() {
		throw new UnsupportedOperationException();
	}

}
