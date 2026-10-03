/**
 * Counts the number of nodes in the linked list.
 * @return the number of nodes
 */
public int count() {
	int count = 0;
	Node node = head;

	while (node != null) {
		count++;
		node = node.next;
	}

	return count;
}


/**
 * Reverses the order of the nodes in the linked list.
 */
public void reverse() {
	Node current = head;

	while (current != null) {
		Node temp = current.next;

		current.next = current.prev;
		current.prev = temp;

		current = temp;
	}

	Node temp = head;
	head = tail;
	tail = temp;
}
