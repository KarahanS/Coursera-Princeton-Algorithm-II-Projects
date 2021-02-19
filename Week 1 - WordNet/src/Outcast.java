
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Outcast {
	private final WordNet wordnet;
	// constructor takes a WordNet object
   public Outcast(WordNet wordnet)   {
	   this.wordnet = wordnet;
	   
   }
// given an array of WordNet nouns, return an outcast
   public String outcast(String[] nouns)   {
	   int outcast = -1;
	   int distance = 0;
	   for(int i=0; i<nouns.length; i++) {
		   int temp_distance = 0;
		   for(int j=0; j<nouns.length; j++) {
			   temp_distance += wordnet.distance(nouns[i], nouns[j]);
		   }
		   if(temp_distance > distance) {
			   distance = temp_distance;
			   outcast = i;
		   }
	   }
	   return nouns[outcast];
	   
   }
   public static void main(String[] args) {
	    WordNet wordnet = new WordNet(args[0], args[1]);
	    Outcast outcast = new Outcast(wordnet);
	    for (int t = 2; t < args.length; t++) {
	        In in = new In(args[t]);
	        String[] nouns = in.readAllStrings();
	        StdOut.println(args[t] + ": " + outcast.outcast(nouns));
	    }
	}
}