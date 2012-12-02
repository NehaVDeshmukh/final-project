package student;

/**
 * @author lauraherrle
 * 
 */
public class TrieNode {

	private char character;
	private boolean terminates;
	private HashTable<Character, TrieNode> children;

	/**
	 * Creates a new node to be used specifically in a Trie
	 * 
	 * @param c
	 *            the character the node represents
	 * @param t
	 *            a flag indicating whether this is the terminal character of a
	 *            string
	 */
	public TrieNode(char c, boolean t) {
		character = c;
		terminates = t;
		children = new HashTable<Character, TrieNode>(26);
	}

	/**
	 * @param n
	 * @return true if the child is addded; false if the child already existed
	 */
	public boolean addChild(TrieNode n) {
		if (children.containsKey(n.getCharacter()))
			return false;

		children.put(n.getCharacter(), n);
		return true;
	}

	@SuppressWarnings("javadoc")
	public char getCharacter() {
		return character;
	}

	@SuppressWarnings("javadoc")
	public boolean terminates() {
		return terminates;
	}

	@SuppressWarnings("javadoc")
	public void makeTerminator() {
		terminates = true;
	}
	
	@SuppressWarnings("javadoc")
	public void removeTerminator() {
		terminates = false;
	}

	/**
	 * @param c
	 *            the desired child character
	 * @return the node representation of the requested child
	 * @throws NoSuchFieldException
	 *             if this node does not have the requested child
	 */
	public TrieNode getChild(char c) throws NoSuchFieldException {
		if (!children.containsKey(c))
			throw new NoSuchFieldException("The child " + c
					+ " does not exist.");

		return children.get(c);
	}
}
