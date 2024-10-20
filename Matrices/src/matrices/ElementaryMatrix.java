package matrices;

//CLASS FOR ELEMENTARY MATRICES FOR DETERMINANT

class ElementaryMatrix {
	
	// 0 is used as 'null' for row i and column j b/c
	// practically there cannot be row 0 or column j 0;
	// it starts from row 1 and column 1
	public int i = 0; // row i
	public int j = 0; // row j
	// 0 is used as 'null' for q b/c it will be used
	// either in a row multiple, which will only be
	// used to simplify an element down to a leading 1
	// (so 0 will never be used here), or row addition,
	// which is used to zero out a col w/ the negative
	// form of an element (which cannot be 0).
	public double q = 0; // real number q
		
	public ElementaryMatrix(int i, int j) {
		this.i = i;
		this.j = j;
	}
		
	public ElementaryMatrix(int i, double q) {
		this.i = i;
		this.q = q;
	}
		
	public ElementaryMatrix(int i, int j, double q) {
		this.i = i;
		this.j = j;
		this.q = q;
	}
	
	// For testing purposes
	public String toString() {
		return i + " " + j + " " + q;
	} // End of toString method

} // End of class