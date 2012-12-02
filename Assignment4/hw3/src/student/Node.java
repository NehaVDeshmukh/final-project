package student;

@SuppressWarnings("javadoc")
public class Node<T> {
	private T data;
	private Node<T> next; // null if end of list

	/**
	 * Creates a new node
	 * 
	 * @param data
	 *            the data contained in the node
	 * @param next
	 *            pointer to the next node; null if last node
	 */
	public Node(T data, Node<T> next) {
		this.data = data;
		this.next = next;
	}

	public T getData() {
		return data;
	}

	/**
	 * @return the next node; null if it is the end of the list
	 */
	public Node<T> getNext() {
		return next;
	}

	public void setNext(Node<T> n) {
		next = n;
	}
}
