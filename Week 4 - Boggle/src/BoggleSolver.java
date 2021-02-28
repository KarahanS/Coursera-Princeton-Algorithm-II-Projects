
import java.util.HashSet;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;


public class BoggleSolver {
	private final DeluxeTrie dictionary;	
	private final HashSet<String> lookup;
	private HashSet<String> validWords; 

	
	
	// We should store the words in a TST, so that we can make prefix query easily.
	public BoggleSolver(String[] dictionary) {
		this.dictionary = new DeluxeTrie();
		this.lookup = new HashSet<String>();
		for(int i=0; i<dictionary.length; i++) {
			this.dictionary.put(dictionary[i], 1);   // all strings have the value of 1
			lookup.add(dictionary[i]);
			// This code may be improved, because new words come with an alphabetical order.
			// The thing is, if we have to check that dictionary[i] starts with dictionary[i-1], it takes extra time
			// So for now, this is as it is without any modification.
		}
	
		
	}


	public Iterable<String> getAllValidWords(BoggleBoard board) {
	// We can cache the boards and valid words, if we encounter with same board we can use the cached values.
		
		validWords = new HashSet<String>();
		boolean[][] marked = new boolean[board.rows()][board.cols()];
	
		for(int i=0; i<board.rows(); i++) {
			for(int j=0; j<board.cols(); j++) {
				// Now we have to implement some sort of a DFS
				
				// i - row,   j - column
				char c = board.getLetter(i, j);
				marked[i][j] = true;
						
				
				String word = "" + c;
				if(c == 'Q') word += "U";

				DeluxeTrie.Node cache = dictionary.wordsWithPrefix(word);
				if(scoreOf(word) > 0) validWords.add(word);
				if(cache != null)  {
					if(i != 0) getAllValidWords(i-1, j, word, cache, marked, board);
					if(j != 0) getAllValidWords(i, j-1, word, cache, marked, board);
					if(i != board.rows() - 1) getAllValidWords(i+1, j, word, cache, marked, board);
					if(j != board.cols() - 1) getAllValidWords(i, j+1, word, cache, marked, board);
					if(i != 0 && j != 0) getAllValidWords(i-1, j-1, word, cache, marked, board);
					if(i != 0 && j != board.cols() - 1) getAllValidWords(i-1, j+1, word, cache, marked, board);
					if(i != board.rows() - 1 && j != 0) getAllValidWords(i+1, j-1, word, cache, marked, board);
					if(i != board.rows() - 1 && j != board.cols() - 1) getAllValidWords(i+1, j+1, word, cache, marked, board);
				}
				marked[i][j] = false;
			}
		}
		return validWords;
	}
	private void getAllValidWords(int row, int col, String word, DeluxeTrie.Node cache, boolean[][] marked, BoggleBoard board) {
		if(marked[row][col]) return;

		
		char c = board.getLetter(row, col);
		word = word + c;
		if(c == 'Q') {
			word += "U";
			cache = dictionary.wordsWithPrefix(word, cache, word.length() - 2);
		} else cache = dictionary.wordsWithPrefix(word, cache, word.length() - 1); 
		
		if(scoreOf(word) > 0) validWords.add(word);
		marked[row][col] = true;
		if(cache != null) {
			if(row != 0 ) getAllValidWords(row-1, col, word, cache, marked, board);
			if(col != 0) getAllValidWords(row, col-1, word, cache, marked, board);
			if(row != board.rows() - 1) getAllValidWords(row+1, col, word, cache, marked, board);
			if(col != board.cols() - 1 ) getAllValidWords(row, col+1, word, cache, marked, board);
			if(row != 0 && col != 0) getAllValidWords(row-1, col-1, word, cache, marked, board);
			if(row != 0 && col != board.cols() - 1) getAllValidWords(row-1, col+1, word, cache, marked, board);
			if(row != board.rows() - 1 && col != 0 ) getAllValidWords(row+1, col-1, word, cache, marked, board);
			if(row != board.rows() - 1 && col != board.cols() - 1 ) getAllValidWords(row+1, col+1, word, cache, marked, board);
		}
		marked[row][col] = false; // backtracking
		
	}
	
	public int scoreOf(String word) {
		if(word == null) throw new IllegalArgumentException();
		if(!lookup.contains(word) || word.length() < 3) return 0;
		
		
		switch (word.length()) {
		case 3 : case 4:
			return 1;
		case 5:
			return 2;
		case 6:
			return 3;
		case 7:
			return 5;
		default:
			return 11;
		}

		    
	}
	public static void main(String[] args) {
	    In in = new In(args[0]);
	    String[] dictionary = in.readAllStrings();
	    BoggleSolver solver = new BoggleSolver(dictionary);
	    BoggleBoard board = new BoggleBoard(args[1]);
	    int score = 0;
	    for (String word : solver.getAllValidWords(board)) {
	        StdOut.println(word);
	        score += solver.scoreOf(word);
	    }
	    StdOut.println("Score = " + score);
	}
}
