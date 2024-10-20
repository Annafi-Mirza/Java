package matrices;

public class Determinant {
	
	// Returns the determinant of elementary and square matrices
	public static double getDeterminant(Object matrix) {
		
		// Returns the determinant of elementary matrices
		if (matrix instanceof ElementaryMatrix) {
			ElementaryMatrix elementary = (ElementaryMatrix)matrix;
			
			// If it represents row addition
			if (elementary.q != 0 && elementary.j != 0) {
				return 1;
			}
			// If it represents a row multiple
			else if (elementary.j == 0) {
				return elementary.q;
			}
			// If it represents a row swap
			else {
				return -1;
			}
		} // End of getting determinant of elementary matrix
		
		// If object is not any type of matrix
		if (!(matrix instanceof double[][])) {
			throw new IllegalArgumentException("Parameter not a matrix!");
		}
		
		// Converts object parameter to square matrix
		double[][] actualArray = (double[][])matrix;
		
		switch(actualArray.length) {
		
			// If matrix contains only 1 element
			case 1:
				return actualArray[0][0];

			// If matrix is a 2x2, the determinant
			// is the difference in the diagonals
			case 2:
				return (actualArray[0][0] * actualArray[1][1]) - 
						(actualArray[0][1] * actualArray[1][0]);
				
			// If matrix is a 3x3, use co-factor expansion
			// along the 1st column
			case 3:
				double sum = 0;
				for (int i = 0; i < actualArray.length; i++) {
					sum += ( Math.pow(-1, i+2) * actualArray[i][0] * 
							getDeterminant(minor(actualArray, i, 0)) );
				}
				return sum;
				
			// If matrix is 4x4 or greater, use Gauss-Jordan algorithm
			// & its elementary matrices, as co-factor expansion summands
			// is n! so it would take too much time and space
			default:
				actualArray = GaussJordan.reduceToRRE(actualArray, 1);
				// Check if matrix has a bottom row of 0s. If it does,
				// the determinant is 0. If not, it is the product of
				// the inverse of each elementary matrix
				boolean hasBottomRowOfZeros = true;
				for (int i = actualArray.length - 1; i >= 0; i--) {
					if (actualArray[actualArray.length - 1][i] != 0) {
						hasBottomRowOfZeros = false;
						break;
					}
				}
				if (hasBottomRowOfZeros) {
					return 0;
				}

				double product = 1;
				// Iterates through list of elementary matrices
				for (int i = 0; i < GaussJordan.RREsteps.size(); i++) {
					// Multiplies each determinant of each inverse of each elementary matrix
					product *= getDeterminant( Inverse.getInverse(GaussJordan.RREsteps.get(i)) );
				}
				return Math.round(product * 1000) / 1000.0;
				
		} // End of deciding based on array size
		
	} // End of getDeterminant method
	
	// HELPER METHODS
	
	// Obtains the minor of a matrix, or a matrix w/
	// a specific row and column removed
	private static double[][] minor(double[][] array, int row, int col) {
		
		// Creates new square matrix of one length less
		double[][] newArray = new double[array.length-1][array.length-1];
		
		// Tracks which position in minor array
		// the elements are being added to
		int minorRow = 0;
		int minorCol = 0;
		
		// Iterates through initial array and selects values
		// from rows and col. that are not spliced out and
		// inserts them into new array
		for (int i = 0; i < array.length; i++) {
			for (int j = 0; j < array.length; j++) {
				
				// If element is not from to-be-spliced
				// row or column, and index does not go
				// beyond new array's length
				if (i != row && j != col && minorRow != newArray.length) {
					// If did not reach right-side of new matrix yet
					if (minorCol != newArray.length) {
						newArray[minorRow][minorCol] = array[i][j];
						++minorCol;
					}
					// Hit right side of matrix, go to next row and
					// reset column index
					else {
						++minorRow;
						minorCol = 0;
						newArray[minorRow][minorCol] = array[i][j];
						++minorCol;
					}
				}
				
			} // End of iterating through columns
		} // End of iterating through rows
		
		return newArray;
		
	} // End of minor method

} // End of class