import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

public class MoveToFront {
	private static final int EXTENDED_ASCII = 256;
	
    // apply move-to-front encoding, reading from standard input and writing to standard output
    public static void encode()  {
    	
    	char[] sequence = new char[EXTENDED_ASCII];
    	for(int i=0; i<sequence.length; i++) {
    		sequence[i] = (char)i;
    	}
    	// O(n * R)
    	while(!BinaryStdIn.isEmpty()) {
    		char c = BinaryStdIn.readChar();
    		int i = 0;
    		// O(R)
    		for(i=0; i<sequence.length; i++) {
    			if(sequence[i] == c) {
    				BinaryStdOut.write(i, 8);
    				break;
    			}
    		}
    		// O(R)
    		for(int j=i; j>0; j--) {
    			sequence[j] = sequence[j-1];
    		}
    		sequence[0] = c;
    	}
    	BinaryStdOut.close();
    }

    // apply move-to-front decoding, reading from standard input and writing to standard output
    public static void decode() {
    	char[] sequence = new char[EXTENDED_ASCII];
    	for(int i=0; i<sequence.length; i++) {
    		sequence[i] = (char)i;
    	}
    	while(!BinaryStdIn.isEmpty()) {
    		char c = BinaryStdIn.readChar();
    		
    		char write = sequence[c];
    		BinaryStdOut.write(write);
    		// O(R)
    		for(int j=c; j>0; j--) {
    			sequence[j] = sequence[j-1];
    		}
    		sequence[0] = write;
    	}
    	BinaryStdOut.close();
    	
    }

    // if args[0] is "-", apply move-to-front encoding
    // if args[0] is "+", apply move-to-front decoding
    public static void main(String[] args) {
    	if(args[0].equals("-")) encode();
    	if(args[0].equals("+")) decode();
    
    }
}
