import java.util.Iterator;
import java.util.NoSuchElementException;

// Last in first out implementation
public class Bag<Item> implements Iterable<Item>{
	private Node<Item> first;
	private int n;
	
	private static class Node<Item> {
		private Item item;
		private Node<Item> next;
	}
	
	public Bag() {
		first = null;
		n = 0;
	}
	public boolean isEmpty() {
		return first == null;
	}
	public int size() {
		return n;
	}
	// add the item to the front, so that it takes O(1) rather than O(n)
	public void add(Item item) {
		Node<Item> previousFirst = this.first;
		first = new Node<Item>();
		first.item = item;
		first.next = previousFirst;
		
	}
	public Iterator<Item> iterator() {
		return new CustomIterator();
	}
	
	private class CustomIterator implements Iterator<Item> {
		private Node<Item> current;
		
		public CustomIterator() {
			current = first;
		}

		@Override
		public boolean hasNext() {
			return current != null;
		}

		@Override
		public Item next() {
			if(!hasNext()) throw new NoSuchElementException();
			Item item = current.item;
			current = current.next;
			return item;
		}
	}
}
