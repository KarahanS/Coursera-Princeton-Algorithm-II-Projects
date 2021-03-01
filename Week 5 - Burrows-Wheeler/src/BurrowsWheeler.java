import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

public class BurrowsWheeler {

    // apply Burrows-Wheeler transform,
    // reading from standard input and writing to standard output 
    public static void transform() {
    	String data = BinaryStdIn.readString(); // it returns all the data
    	CircularSuffixArray csa = new CircularSuffixArray(data);
 
    	char[] t = new char[csa.length()];
    	
    	
    	// index[i] = j  means  j th original suffix appears ith in the sorted order
      	for(int i=0; i<csa.length(); i++) {
      		int j = csa.index(i);
      		t[i] = (j != 0) ? data.charAt(j - 1) : data.charAt(data.length() - 1);
      		
    		if(j == 0) {
    			BinaryStdOut.write(i); // 32 bit - 4 bytes to represent
    			
    		}
    	}
      	for(int i=0; i<t.length; i++) BinaryStdOut.write(t[i]); // not sure, but we have to represent characters by hex 
      	BinaryStdOut.close();
    	
    	
    }

    // apply Burrows-Wheeler inverse transform,
    // reading from standard input and writing to standard output
    public static void inverseTransform() {

    	int first = BinaryStdIn.readInt();
    	String t = BinaryStdIn.readString();

    
    	char[] first_row = new char[t.length()];
    	// O(n)
    	for(int i=0; i<t.length(); i++) first_row[i] = t.charAt(i);
    	int[] next = new int[t.length()];
    	Radix(first_row, next);
   
    	BinaryStdOut.write(first_row[first]);
    	int k = first;
    	
    	for(int i=1; i<t.length(); i++) {
    		BinaryStdOut.write(first_row[next[k]]);
    		k = next[k];
    	}
    	
    
    	BinaryStdOut.close();
    	
    	
    	
    }

	private static void Radix(char[] a, int[] next) {
		
		int max = 256;  
		int[] count = new int[max + 2];
		
		for(int i=0; i<a.length; i++) {  
			count[a[i] + 1]++;         
		}
		
		for(int i=1; i<count.length; i++) {   
			count[i] += count[i - 1];
		}
		
		char aux[] = new char[a.length];
		
		for(int i=0; i<a.length; i++) {  
			aux[count[a[i]]] = a[i];  
			/*
			 * So, i is the index of that character in t[].
			 * And count[a[i]] is the index of it in first_row[].
			 */
			
			next[count[a[i]]] = i;
			count[a[i]]++;     
			
		}
		
		for(int i=0; i<a.length; i++) { 
			a[i] = aux[i];
		}
	}

    // if args[0] is "-", apply Burrows-Wheeler transform
    // if args[0] is "+", apply Burrows-Wheeler inverse transform
    public static void main(String[] args) {
    	if(args[0].equals("-")) transform();
    	if(args[0].equals("+")) inverseTransform();
    	

    	
    }

}