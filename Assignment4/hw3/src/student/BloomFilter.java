package student;

import java.util.Collection;

/**
 * A collection of elements of type E for which the only operation is a
 * probabilistic membership test.
 */
@SuppressWarnings("javadoc")
public class BloomFilter<E> {

	long[] elements;
	int numHash;

	/**
	 * Return a new Bloom filter with the elems inside
	 * 
	 * @param initialSize
	 *            The initial size of the BloomFilter
	 * @param numHashFunctions
	 *            The number of times the hash function should be applied to
	 *            each new element
	 */
	public BloomFilter(Collection<E> elems, int initialSize,
			int numHashFunctions) {
		numHash = numHashFunctions;
		elements = new long[(int) Math.floor(((double) initialSize / 64) + 1)];
		if (elems != null) {
			for (E e : elems)
				insert(e);
		}
	}

	public void clear() {
		for (int i = 0; i < elements.length; i++)
			elements[i] = 0;
	}

	/**
	 * Add elem to the BloomFilter
	 */
	public void insert(E elem) {
		int h;
		Object e = elem;

		for (int i = 0; i < numHash; i++) {
			h = e.hashCode();
			setBit(h);
			e = Integer.toString(h);
			e = (String) e + 'a';
		}
	}

	/**
	 * Check whether elem might be in the collection. A result of false means it
	 * definitely isn't.
	 */
	public boolean mightContain(E elem) {
		int h;
		Object e = elem;
		boolean bit;

		for (int i = 0; i < numHash; i++) {
			h = e.hashCode();
			bit = (getBit(h) == 1);
			if (!bit)
				return false;

			e = Integer.toString(h);
			e = (String) e + 'a';
		}

		return true;
	}

	private void setBit(int hash) {
		if (hash < 0)
			hash = -hash;
		int index = (hash / 64) % elements.length;
		int bit = hash % 64;
		long mask = 1;
		for (int i = 0; i < bit; i++)
			mask *= 2;

		elements[index] |= mask;
	}

	private int getBit(int hash) {
		if (hash < 0)
			hash = -hash;
		int index = (hash / 64) % elements.length;
		int bit = hash % 64;
		long mask = 1;
		for (int i = 0; i < bit; i++)
			mask *= 2;

		if ((elements[index] & mask) != 0)
			return 1;

		return 0;
	}
}
