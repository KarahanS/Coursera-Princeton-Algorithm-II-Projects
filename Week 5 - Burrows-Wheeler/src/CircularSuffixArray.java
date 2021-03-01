public class CircularSuffixArray {
	private int[] index;
	
	private class CircularSuffix {
		int start;

		
		/*
		 *  starting index is given
		 *  circularsuffix.charAt(0) = string.charAt(index + 0)
		 *  circularsuffix.charAt(a) = string.charAt(index + a)                    if (index + a) < string.length
		 *                           = string.charAt(index + a - string.length())  if (index + a) >= string.length
		 */

		CircularSuffix(int start) {
			this.start = start;

		}
	}
    // circular suffix array of s
    public CircularSuffixArray(String s) {
    	if(s == null) throw new IllegalArgumentException();
    	index = new int[s.length()];
    	
    	// index[i]   i: suffix with start = i
    	CircularSuffix[] suffixes = new CircularSuffix[s.length()];
    	
    	for(int i=0; i<s.length(); i++) suffixes[i] = new CircularSuffix(i);
    	
    	sort(suffixes, 0, suffixes.length - 1, 0, s);
    	for(int i=0; i<index.length; i++) {
    		index[i] = suffixes[i].start;
    	}
    	
    	
    	
    }

    // length of s
    public int length() {
    	return index.length;
    }

    // returns index of ith sorted suffix
    public int index(int i) {
    	if( i < 0 || i >= index.length) throw new IllegalArgumentException();
    	return index[i];
    }
    
    private void sort(CircularSuffix[] a, int lo, int hi, int index, String s) {
    	if(lo >= hi) return;
    	int left = lo;
    	int right = hi;
    	int i = lo;
    	int v = charAt(a[i++].start, index, s); 
    	while(i<=right) {
    		int t = charAt(a[i].start, index, s);
    		if( t < v) {
    			CircularSuffix temp = a[left];
    			a[left] = a[i];
    			a[i] = temp;

    			i++;
    			left++;
    		}
    		else if (t > v) {
    			CircularSuffix temp = a[right];
    			a[right] = a[i];
    			a[i] = temp;

    			right--;
    		}
    		else i++;
    	}
    	
    	sort(a, lo, left-1, index, s);
    	if(v >= 0) sort(a, left, right, index+1, s);
    	sort(a, right+1, hi, index, s);
    }
    
    
	
	/*
	 *  circularsuffix.charAt(0) = string.charAt(index + 0)
	 *  circularsuffix.charAt(a) = string.charAt(index + a)                    if (index + a) < string.length
	 *                           = string.charAt(index + a - string.length())  if (index + a) >= string.length
	 */

	// string.charAt() is O(1)
	private int charAt(int start, int a, String s) {
		if(start + a < s.length()) return s.charAt(start + a);
		else if (a < s.length()) return s.charAt(start + a - s.length());
		else return -1;
	}

    // unit testing (required)
    public static void main(String[] args) {
    	CircularSuffixArray csa = new CircularSuffixArray("ABRACADABRA!");
    	System.out.println(csa.length());
    	System.out.println("----");
    	for(int i=0; i<csa.length(); i++) System.out.println(csa.index(i));
    	
    }

}