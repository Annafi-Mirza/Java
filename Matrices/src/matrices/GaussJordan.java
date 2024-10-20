package matrices;

// Because the 2D array already takes up quite a bit of
// space, a linked list is used here instead to save a
// bit on storage. Also, the O(N) of linked lists'
// insertion or deletion methods doesn't matter here b/c
// it will be treated like a queue or stack
import java.util.LinkedList; // Manage elementary matrices

public class GaussJordan {
	
	// Keeps track of changes made to matrix during Gauss-Jordan elimination
	// to eventually calculate the determinant if need be
	public static LinkedList<ElementaryMatrix> RREsteps = new LinkedList<ElementaryMatrix>();
	
	// Reduce a matrix to RRE form to grab solutions to a system of equations
	public static double[][] reduceToRRE(double[][] matrix, int whetherInverse) {
		
		// DOWNWARD ALGORITHM
		
		int leadingOneRow = 0; // Keeps track of how many
		// leading 1s there are
		
		// Initiates i as i is changed throughout the downward algorithm
		int i = 0;
		
		// Iterates column-major order
		for (int j = 0; j < matrix[0].length; j++) {
			for (i = i; i < matrix.length; i++) {
				
				// Finds leftmost non-zero
				if (matrix[i][j] != 0) {
					
					// If non-zero is not in the next leading row
					if (i != leadingOneRow) {
						swapTwoRows(matrix, i, leadingOneRow);
						// ^ Swaps the rows
						RREsteps.add(new ElementaryMatrix(leadingOneRow+1, i+1));
						// ^ Creates new elementary matrix to track this swapping
					} // End of swapping rows
					
					// Converts leading non-zero to leading 1
					if (matrix[leadingOneRow][j] != 1) {
						RREsteps.add( new ElementaryMatrix( leadingOneRow+1, (1.0 / matrix[leadingOneRow][j]) ) );
						makeLeadingOne(matrix, leadingOneRow, matrix[leadingOneRow][j]);
						// ^ Creates new elementary matrix to track this conversion
					}
					
					// Zeros out column below leading 1
					for (int x = leadingOneRow + 1; x < matrix.length; x++) {
						
						// Adds to each row w/ non-zero element under leading 1,
						// the leading 1 element times the negative form of
						// non-zero element
						if (matrix[x][j] != 0) {
							RREsteps.add(new ElementaryMatrix(x+1, leadingOneRow+1, -matrix[x][j]));
							addTwoRows(matrix, x, leadingOneRow, j, matrix[x][j]);
							// ^ Creates new elementary matrix to track this addition
						}
						
					} // End of iterating through remaining rows
					
					// Breaks out of iteration of rows as
					// they're all zero-ed out
					break;
					
				} // End of dealing with leftmost non-zero row
				
			} // End of iterating through columns
			
			if (leadingOneRow != matrix.length) {
				++leadingOneRow;
			}
			// ^ New leading 1 row created
			i = leadingOneRow;
			
		} // End of iterating through rows
		
		// UPWARD ALGORITHM
		
		int d = matrix[0].length - 1;
		if (whetherInverse == 2) {
			d = (matrix[0].length / 2) - 1;
		}
		
		// Condition is to prevent situations where
		// 1z = 1 and it recognizes the 2nd 1 as the
		// leading 1
		if (matrix[leadingOneRow - 1][d - 1] == 1.0) {
			d = d - 1;
		}

		int bottomLeadingOne = 0; // Position of bottom-most
		// leading 1
		boolean leadingOneFound = false; // Tracks if bottom-most
		// leading 1 is found
		
		for (int c = leadingOneRow - 1; c >= 0; c--) {
			for (d = d; d >= 0; d--) {
				
				// When it finds a leading 1
				if (matrix[c][d] == 1.0) {
					
					// Zeros out numbers above leading 1
					for (int e = c - 1; e >= 0; e--) {
						// If number above is not already 0
						if (matrix[e][d] != 0) {
							// Adds to row above, the bottom leading 1 row
							// times the negative form of the row above's
							// element
							RREsteps.add( new ElementaryMatrix(e, c, -matrix[e][d]) );
							addTwoRows(matrix, e, c, 0, matrix[e][d]);
							// ^ Creates a new elementary matrix to track this addition
						}
					} // End of zero-ing out above bottom-most leading 1
					
					leadingOneFound = true;
					bottomLeadingOne = d;
					// ^ Records bottom leading 1's position
					
					// Exit column loop b/c leading 1 was found
					break;
					
				}
				
			} // End of iterating through columns backward
			
			// Now starts iterating from diagonal (upper left)
			// to previous leading 1 to save time
			if (leadingOneFound) {
				d = bottomLeadingOne - 1;
			}
			
		} // End of iterating through rows upward
		
		return matrix;
	} // End of reduceToRRE method
	
	// HELPER METHODS - ELEMENTARY MATRICES
	
	// Swaps two rows of a matrix
	private static void swapTwoRows(double[][] matrix, int rowToBeSwapped, int rowToSwapTo) {
		
		// Iterates through each column and swaps each element
		for (int a = 0; a < matrix[0].length; a++) {
			double tempNumber = matrix[rowToSwapTo][a];
			matrix[rowToSwapTo][a] = matrix[rowToBeSwapped][a];
			matrix[rowToBeSwapped][a] = tempNumber;
		} // End of swapping rows
		
	} // End of swapTwoRows method
	
	// Makes leading non-zero to leading 1
	private static void makeLeadingOne(double[][] matrix, int row, double nonZero) {
		
		// Iterates through leading non-zero row and
		// multiplies each element by non-zero's reciprocal
		for (int a = 0; a < matrix[0].length; a++) {
			matrix[row][a] *= (1.0 / nonZero);
		} // End of conversion
		
	} // End of makeLeadingOne method
	
	// Adds to 1 row, another row times a multiple
	private static void addTwoRows(double[][] matrix, int rowToBeChanged, int leadingOneRow, 
			int startingCol, double element) {
		
		// Adds to non-zero row, the leading 1 row times the non-zero row 1st element's
		// negative form
		for (int a = startingCol; a < matrix[0].length; a++) {
			matrix[rowToBeChanged][a] += (matrix[leadingOneRow][a] * -element);
		} // End of iterating adding each pair of elements
		
	} // End of addTwoRows
	
	// Analyzes the solutions, if any, of an RRE matrix
	public static void analyzeSolutions(double[][] matrix) {
		
		for (int i = 0; i < matrix.length; i++) {
			
			// Tracks what variable is being looked for in matrix
			switch(i) {
				case 0:
					System.out.println("x = " + matrix[i][matrix[0].length - 1]);
					break;
				case 1:
					System.out.println("y = " + matrix[i][matrix[0].length - 1]);
					break;
				case 2:
					System.out.println("z = " + matrix[i][matrix[0].length - 1] + "\n");
					break;
			}
			
		} // End of iterating through rows
		
	} // End of analyzeSolutions method

} // End of class