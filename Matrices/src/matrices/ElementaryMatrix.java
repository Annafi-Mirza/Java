package matrices;

//CLASS FOR ELEMENTARY MATRICES FOR DETERMINANT

class ElementaryMatrix {
		
	public int i; // row i
	public int j; // row j
	public double q; // real number q
		
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

} // End of class