import java.util.ArrayList;
import java.util.HashMap;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.Stack;

// Immutable data type
public class WordNet {
	private final HashMap<String, ArrayList<Integer>> nouns;
	private final ArrayList<String> synset;
	private final SAP sap;

   // constructor takes the name of the two input files
	// We need to check if our graph is a DAP (We can check with topological sort)
   public WordNet(String synsets, String hypernyms) {
	   if(synsets == null || hypernyms == null) throw new IllegalArgumentException();
	   In readSynsets = new In(synsets);
	   In readHypernyms = new In(hypernyms);
	   
	   
	 
	   nouns = new HashMap<String, ArrayList<Integer>>();
	   synset = new ArrayList<String>();
	   
	   while(!readSynsets.isEmpty()) {
		   String str = readSynsets.readLine();
		   String[] divided = str.split(",");
		   synset.add(divided[1]);
		   int id = Integer.parseInt(divided[0]);
		   
		   String[] noun = divided[1].split(" ");
		   for(int i=0; i<noun.length; i++) {
			   String word = noun[i];
			   if(nouns.containsKey(word)) {
				   nouns.get(word).add(id);
			   } else {
				   ArrayList<Integer> val = new ArrayList<Integer>();
				   val.add(id);
				   nouns.put(word, val);
			   }
		   }
		   
	   }
	   Digraph wordnet = new Digraph(synset.size());
	   while(!readHypernyms.isEmpty()) {
		   String[] str = readHypernyms.readLine().split(",");
		   int id = Integer.parseInt(str[0]);
		   for(int i=1; i<str.length; i++) {
			   int hyper = Integer.parseInt(str[i]);
			   wordnet.addEdge(id, hyper);
		   }
	   }
	   if(!TopologicalSort(wordnet)) throw new IllegalArgumentException();
	   sap = new SAP(wordnet);
	   
   }
	private boolean TopologicalSort(Digraph G) {
		   int noSorted = 0;
		   boolean[] marked = new boolean[G.V()];
		   int[] inDegree = new int[G.V()];
		   for(int v=0; v<G.V(); v++) {
			   for(int w: G.adj(v)) {
				   inDegree[w]++;
			   }
		   }
		   
		   Queue<Integer> q = new Queue<Integer>();
		   for(int i=0; i<inDegree.length; i++) {
			   if(inDegree[i] == 0) q.enqueue(i);
		   }
		   if(q.size() == 0) return false;  // cycle
		   boolean rootFound = false;
		   while(!q.isEmpty()) {
			   boolean check = false;
			   int v = q.dequeue();
			   noSorted++;
			   marked[v] = true;
			   
			   for(int w: G.adj(v)) {
				   check = true;
				   inDegree[w]--;
				   if(inDegree[w] == 0 && !marked[w]) q.enqueue(w);
				 }
			   if(!check) {
				   if(!rootFound) rootFound = true;
				   else return false;  // more than 1 root
			   }
		   }
		   if(noSorted != G.V()) return false;
		   return true;
		   
	   }

   // returns all WordNet nouns
   public Iterable<String> nouns() {
	   Stack<String> stack = new Stack<String>();
	   for(String key: nouns.keySet()) {
		   stack.push(key);
	   }
	   return stack;
   }

   // is the word a WordNet noun?
   public boolean isNoun(String word) {
	   if(word == null) throw new IllegalArgumentException();
	   if(nouns.containsKey(word)) return true;
	   else return false;
	   
   }

   // distance between nounA and nounB (defined below)
   public int distance(String nounA, String nounB) {
	   if(nounA == null || nounB == null || !isNoun(nounA) || !isNoun(nounB)) throw new IllegalArgumentException();
	   
	   Stack<Integer> setA = new Stack<Integer>();
	   Stack<Integer> setB = new Stack<Integer>();
	   for(int id: nouns.get(nounA)) setA.push(id);
	   for(int id: nouns.get(nounB)) setB.push(id);
	   return sap.length(setA, setB);
   }

   // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
   // in a shortest ancestral path (defined below)
   public String sap(String nounA, String nounB) {
	   if(nounA == null || nounB == null || !isNoun(nounA) || !isNoun(nounB)) throw new IllegalArgumentException();
	
	   Stack<Integer> setA = new Stack<Integer>();
	   Stack<Integer> setB = new Stack<Integer>();
	   for(int id: nouns.get(nounA)) setA.push(id);
	   for(int id: nouns.get(nounB)) setB.push(id);
	   int ancestorID = sap.ancestor(setA, setB);
	   return synset.get(ancestorID);
   }

   // do unit testing of this class

}