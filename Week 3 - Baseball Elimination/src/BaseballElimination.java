import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;



public class BaseballElimination {
	private final Map<String, Integer> hash;
	private final int[] w;    // wins
	private final int[] l;    // losses
	private final int[] r;    // remaining hames
	private final int[][] g;  // games left to play agains team j
	private boolean[] eliminated;
	private Queue<String>[] certificates;
	
	public BaseballElimination(String filename){
		// create a baseball division from given filename in format specified below
		
		Scanner scan = null;
		try {
			scan = new Scanner(new File(filename));
		} catch (FileNotFoundException e) {}
		
		int size = scan.nextInt();
		
		w = new int[size];
		l = new int[size];
		r = new int[size];
		g = new int[size][size];
		eliminated = new boolean[size];
		certificates = (Queue<String>[]) new Queue[size];
		hash = new HashMap<String, Integer>();
		
		for(int i=0; i<size; i++) {
			String team = scan.next();
			hash.put(team, i);
			w[i] = scan.nextInt();
			l[i] = scan.nextInt();
			r[i] = scan.nextInt();
			for(int j=0; j<size; j++) g[i][j] = scan.nextInt();
		}
		for(String team: hash.keySet()) {
			elimination(team);
		}
		
	}
	private void elimination(String team) {
		int index = indexOf(team);
		boolean check = false;
		Queue<String> opponents = new LinkedList<String>();
		
		for(String str: hash.keySet()) {
			int i = indexOf(str);
			if(w[index] + r[index] < w[i]) {
				// trivial elimination
				check = true;
				opponents.add(str);
			}
		}
		if(check) {
			certificates[index] = opponents;
			eliminated[index] = true;
			return;
		}
		
		// nontrivial elimination
		
		// s --> game vertices --> team vertices --> t
		int games = (numberOfTeams() * (numberOfTeams() - 1)) / 2;
		int s = 0;
		int t = games + numberOfTeams() + 1;
		FlowNetwork network = new FlowNetwork(t - s + 1);
		
		/*
		 * Indexes
		 * 
		 *  0 = s
		 *  1 = games 
		 *  2 = games
		 *  ...
		 *  games + 1  = team
		 *  games + 2  = team
		 *  ...
		 *  games + team + 1 = t
		 */
		int home = 0;
		int guest = home + 1;
		for(int i=1; i<=games; i++) {
			// we have to find which game it is.
			network.addEdge(new FlowEdge(0, i, g[home][guest]));


			int home_vertex = games + 1 + home;
			int guest_vertex = games + 1 + guest; // (games + (guest + 1) + 1 = t
				
			network.addEdge(new FlowEdge(i, home_vertex, Double.MAX_VALUE));
			network.addEdge(new FlowEdge(i, guest_vertex, Double.MAX_VALUE));
			if(guest == numberOfTeams() - 1) {
				home++;
				guest = home + 1;
			} else guest++;

		}
		for(int i=0; i<numberOfTeams(); i++) {
			int vertex = i + games + 1;
			network.addEdge(new FlowEdge(vertex, t, w[index] + r[index] - w[i]));
		}
		
		FordFulkerson ford = new FordFulkerson(network, s, t);
		Queue<String> R = new LinkedList<String>();
		
		int totalWins = 0;
		int totalRemaining = 0;
		int totalTeams = 0;
		for(String str: hash.keySet()) {
			int i = indexOf(str);
			int vertex = i + games + 1;
			if(i != index && ford.inCut(vertex)) {
				totalWins += w[i];
				totalRemaining += r[i];
				totalTeams ++;
				R.add(str);
			}
		}
		// check the elimination
		if (( (double)(totalWins + totalRemaining) / totalTeams) > r[index] ) eliminated[index] = true;
		certificates[index] = R;
		
	}
	
	

	public int numberOfTeams() {
		return hash.size();
	}

	public Iterable<String> teams() {
		return hash.keySet();
	}
	private int indexOf(String team) {
		Integer index = hash.get(team);
		if (index != null) return index;
		else throw new IllegalArgumentException();
		
	}

	public int wins(String team) {
		int index = indexOf(team);
		return w[index];
	}

	public int losses(String team) {
		int index = indexOf(team);
		return l[index];
	}

	public int remaining(String team) {
		int index = indexOf(team);
		return r[index];
	}

	public int against(String team1, String team2) {
		int index1 = indexOf(team1);
		int index2 = indexOf(team2);
		
		return g[index1][index2];

	}

	public boolean isEliminated(String team) {
		int index = indexOf(team);
		return eliminated[index];
	}

	public Iterable<String> certificateOfElimination(String team) {
		int index = indexOf(team);
		if(certificates[index].size() == 0) return null;
		else return certificates[index];
	}
	public static void main(String[] args){
	    BaseballElimination division = new BaseballElimination(args[0]);
	    for (String team : division.teams()) {
	        if (division.isEliminated(team)) {
	            System.out.print(team + " is eliminated by the subset R = { ");
	            for (String t : division.certificateOfElimination(team)) {
	            	 System.out.print(t + " ");
	            }
	            System.out.println("}");
	        }
	        else {
	        	 System.out.println(team + " is not eliminated");
	        }
	    }
	}
}
