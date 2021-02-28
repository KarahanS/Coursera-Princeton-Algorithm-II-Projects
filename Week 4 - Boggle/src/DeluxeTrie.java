public class DeluxeTrie {
	
	private static final int R = 26;  // A to Z   A = 65
	private Node root;
	
	public class Node {
		private int value = 0;
		private boolean continuity = false;
		private Node[] children = new Node[R];
		
	}
	
	DeluxeTrie() {
		this.root = new Node(); // artificial root
	}
	public void put(String key, int value) {
		root = put(root, key, value, 0);
	}
	private Node put(Node node, String key, int value, int index) {
		if(node == null) node = new Node();
	
		if(index == key.length()) {
			node.value = value;   // value of this node is updated. 
			return node;
		}
		
		char c = key.charAt(index);
		node.continuity = true;
		node.children[c - 65] = put(node.children[c - 65], key, value, index + 1);
		
		return node;
	
	}
	public int get(String key) {
		Node x = get(root, key, 0);
		if(x== null) return 0; // not found
		return x.value;
	
	}
	private Node get(Node node, String key, int index) {
		if(node == null) return null; // not found
		if(index == key.length()) return node; // found
		
		char c = key.charAt(index);
		return get(node.children[c - 65], key, index + 1);
	
	
	}

	/*
	 * Exploit that fact that when you perform a prefix query operation, 
	 * it is usually almost identical to the previous prefix query, except that it is 
	 * one letter longer.
	 */
	// node is the node of previous prefix search
	public Node wordsWithPrefix(String prefix, Node node, int index) { // index should be the length of last word (or (length - 1) of this word)
		node = get(node, prefix, index);  // value of the node is going to be changed, but it is not a problem since all characters are in boggle. 
		                                  // Values are not really important in this implementation

		if(node == null) return null;
		if(node.continuity) return node;
		return null;
		
	}
	public Node wordsWithPrefix(String prefix) {
		Node node = get(root, prefix, 0);
		
		if(node == null) return null;
		if(node.continuity) return node;
		return null;
	}

	

}
