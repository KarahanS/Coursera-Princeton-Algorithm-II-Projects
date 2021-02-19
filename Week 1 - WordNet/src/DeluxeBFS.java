import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.Queue;


public class DeluxeBFS {
	private boolean[] marked;
	private int[] distance;
	
	public DeluxeBFS(Digraph G, Iterable<Integer> sources) {
		marked = new boolean[G.V()];
		distance = new int[G.V()];
		for(int i=0; i<distance.length; i++) distance[i] = Integer.MAX_VALUE;
		bfs(G, sources);
		
	}
	public DeluxeBFS(Digraph G, int source) {
		marked = new boolean[G.V()];
		distance = new int[G.V()];
		for(int i=0; i<distance.length; i++) distance[i] = Integer.MAX_VALUE;
		bfs(G, source);
		
	}
	private void bfs(Digraph G, Iterable<Integer> sources) {
		Queue<Integer> q = new Queue<Integer>();
		for(int s: sources)  {
			q.enqueue(s);
			distance[s] = 0;
		}
		while(!q.isEmpty()) {
			int size = q.size();
			for(int i=0; i<size; i++) {
				int v = q.dequeue();
				for(int w: G.adj(v)) {
					if(distance[w] > distance[v] + 1) {
						q.enqueue(w);
						distance[w] = distance[v] + 1;
					}			
				}
			}	
		}
	}
	private void bfs(Digraph G, int s) {
		Queue<Integer> q = new Queue<Integer>();
		q.enqueue(s);
		marked[s] = true;
		distance[s] = 0;
		
		while(!q.isEmpty()) {		
			int size = q.size();
			for(int i=0; i<size; i++) {
				int v = q.dequeue();
				for(int w: G.adj(v)) {
					if(!marked[w]) {
						distance[w] = distance[v] + 1;
						q.enqueue(w);
						marked[w] = true;
					}
				}
			}
		}
	}

	// Is there a path from s to v?
	public boolean hasPathTo(int v) {
		return distance[v] != Integer.MAX_VALUE;
	}
	public int distanceTo(int u) {
		return distance[u];
	}
}