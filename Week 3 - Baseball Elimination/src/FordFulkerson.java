import edu.princeton.cs.algs4.Queue;

public class FordFulkerson {
	private boolean[] marked;  // true if s->v path in residual network
	private FlowEdge[] edgeTo; // last flow edge on s->v path
	private double value; // value of flow
	
	public FordFulkerson(FlowNetwork G, int s, int t) {
		
		value = 0.0;
		while(hasAugmentingPath(G, s, t)) {
			double bottleneck = Double.POSITIVE_INFINITY;
			
			for(int v=t; v!=s; v=edgeTo[v].other(v)) {
				if(bottleneck > edgeTo[v].residualCapacityTo(v)) {
					bottleneck = edgeTo[v].residualCapacityTo(v);
				}
			}
			for(int v=t; v!=s; v=edgeTo[v].other(v)) {
				edgeTo[v].addResidualFlowTo(v, bottleneck);
			}
			value+= bottleneck;
		}
	}
	
	private boolean hasAugmentingPath(FlowNetwork G, int s, int t) {
		edgeTo = new FlowEdge[G.V()];
		marked = new boolean[G.V()];
		
		Queue<Integer> q = new Queue<Integer>();
		q.enqueue(s);
		
		// BFS Implementation 
		marked[s] = true;
		while(!q.isEmpty()) {
			int v = q.dequeue();
			for(FlowEdge e: G.adj(v)) { // edges from v to other vertices
				int w = e.other(v);
				if(e.residualCapacityTo(w) > 0 && !marked[w]) {
					edgeTo[w] = e;
					marked[w] = true;
					q.enqueue(w);
				}
			}
		}
		return marked[t]; // is t reachable from s in residual network
	}
	public double value() {
		return value;
	}
	public boolean inCut(int v) {  // is v reachable from s in residual network
		return marked[v];
	}
}
