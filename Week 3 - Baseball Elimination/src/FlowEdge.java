
// Forward edge: residual capacity = c - f
// Backward edge : residual capacity = fe
// Capacity doesn't change, but residual capacity changes.

public class FlowEdge {
	private final int v, w;
	private final double capacity;
	private double flow;
	
	public FlowEdge(int v, int w, double capacity) {
		this.v = v;
		this.w = w;
		this.capacity = capacity;
	}
	int from() {
		return v;
	}
	int to() {
		return w;
	}
	double capacity() {
		return capacity;
	}
	double flow() {
		return flow;
	}
	public int other(int vertex) {
		if(v == vertex) return w;
		else if(w == vertex) return v;
		else throw new RuntimeException("Illegal endpoint");
	}
	double residualCapacityTo(int v) {
		if(v==from()) {
			return flow;
		} else {
			return capacity - flow;
		}
	}
	void addResidualFlowTo(int v, double delta) {
		if(v==from()) {
			flow-=delta;  // from w to v
		} else {
			flow+=delta;   // from v to w
		}
	}

}
