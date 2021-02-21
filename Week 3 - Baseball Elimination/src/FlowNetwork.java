
public class FlowNetwork {
	private final int V;
	private Bag<FlowEdge>[] network;
	
	public FlowNetwork(int V) {
		this.V = V;
		network = (Bag<FlowEdge>[]) new Bag[V];
		for(int i=0; i<V; i++) {
			network[i] = new Bag<FlowEdge>();
		}
	}
	public void addEdge(FlowEdge e) {
		int from = e.from();
		int to = e.to();
		// Need to process edge = v-->w in either direction.
		// Include e in both v and w's adjacency lists.
		network[from].add(e);  // forward edge
		network[to].add(e);    // back edge
		
	}
	
	Iterable<FlowEdge> adj(int v) {
		return network[v];
	}
	public int V() {
		return V;
	}
}
