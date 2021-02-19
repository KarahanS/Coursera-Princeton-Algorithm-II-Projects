import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class SAP {
	private final Digraph G;

	// constructor takes a digraph (not necessarily a DAG)
	public SAP(Digraph G) {
		if(G == null) throw new IllegalArgumentException();
		this.G = new Digraph(G); // immutable data type
		

	}

	// length of shortest ancestral path between v and w; -1 if no such path
	public int length(int v, int w) {
		if(v < 0 || v >= G.V() || w < 0 || w >= G.V()) throw new IllegalArgumentException();
		
		DeluxeBFS bfs_v = new DeluxeBFS(G, v);
		DeluxeBFS bfs_w = new DeluxeBFS(G, w);
		
		int length = Integer.MAX_VALUE;
		for(int u=0; u<G.V(); u++) {
			if(bfs_v.hasPathTo(u) && bfs_w.hasPathTo(u)) {
				int temp_length =  bfs_v.distanceTo(u) + bfs_w.distanceTo(u);
				if(temp_length < length) {
					length = temp_length;
				}
			}
		}
		if(length == Integer.MAX_VALUE) length = -1;

		return length;
	}
	

	// a common ancestor of v and w that participates in a shortest ancestral path -1 if no such path
	public int ancestor(int v, int w) {

		if(v < 0 || v >= G.V() || w < 0 || w >= G.V()) throw new IllegalArgumentException();
		
		DeluxeBFS bfs_v = new DeluxeBFS(G, v);
		DeluxeBFS bfs_w = new DeluxeBFS(G, w);
		
		int ancestor = -1;
		int length = Integer.MAX_VALUE;
		
		for(int u=0; u<G.V(); u++) {
			if(bfs_v.hasPathTo(u) && bfs_w.hasPathTo(u)) {
				int temp_length =  bfs_v.distanceTo(u) + bfs_w.distanceTo(u);
				if(temp_length < length) {
					ancestor = u;
					length = temp_length;
				}
			}
		}
		if(length == Integer.MAX_VALUE) length = -1;

		return ancestor;
	}

	// length of shortest ancestral path between any vertex in v and any vertex in
	// w; -1 if no such path
	public int length(Iterable<Integer> v, Iterable<Integer> w) {
		if(!validate(v) || !validate(w)) throw new IllegalArgumentException();
		
		DeluxeBFS bfs_v = new DeluxeBFS(G, v);
		DeluxeBFS bfs_w = new DeluxeBFS(G, w);
		
		
		int length = Integer.MAX_VALUE;
		for(int u=0; u<G.V(); u++) {
			if(bfs_v.hasPathTo(u) && bfs_w.hasPathTo(u)) {
				int temp_length =  bfs_v.distanceTo(u) + bfs_w.distanceTo(u);
				if(temp_length < length) length = temp_length;
				
			}
		}
		if(length == Integer.MAX_VALUE) return -1;
		return length;
	}
	
	private boolean validate(Iterable<Integer> v) {
		if(v == null) return false;
		for(Integer i: v) {
			if (i==null || i<0 || i>=G.V()) return false;
		}
		return true;
	}
	// a common ancestor that participates in shortest ancestral path; -1 if no such path
	public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
		if(!validate(v) || !validate(w)) throw new IllegalArgumentException();
		
		DeluxeBFS bfs_v = new DeluxeBFS(G, v);
		DeluxeBFS bfs_w = new DeluxeBFS(G, w);
		
		int ancestor = -1;
		int length = Integer.MAX_VALUE;
		
		for(int u=0; u<G.V(); u++) {
			if(bfs_v.hasPathTo(u) && bfs_w.hasPathTo(u)) {
				int temp_length =  bfs_v.distanceTo(u) + bfs_w.distanceTo(u);
				if(temp_length < length) {
					ancestor = u;
					length = temp_length;
				}
			}
		}

		return ancestor;
	}

	public static void main(String[] args) {
		In in = new In(args[0]);
		Digraph G = new Digraph(in);
		SAP sap = new SAP(G);
		
		Stack<Integer> A = new Stack<Integer>();
		A.push(7);
		A.push(13);
		A.push(17);
		
		Stack<Integer> B = new Stack<Integer>();
		B.push(18);
		B.push(24);
		B.push(6);
		int length = sap.length(A, B);
		int ancestor = sap.ancestor(A, B);
		StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
	
	}
}