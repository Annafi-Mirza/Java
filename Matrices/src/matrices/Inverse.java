package matrices;

public class Inverse {
	
	// Returns the inverse of an elementary or square matrix
	public static Object getInverse(Object matrix) {
		
		// Returns the inverse of elementary matrices
		if (matrix instanceof ElementaryMatrix) {
			
			ElementaryMatrix elementary = (ElementaryMatrix)matrix;
			
			// If it represents row addition
			if (elementary.q != 0 && elementary.j != 0) {
				elementary.q *= -1;
				return elementary;
			}
			// If it represents a row multiple
			else if (elementary.j == 0) {
				elementary.q = 1 / elementary.q;
				return elementary;
			}
			// If it represents a row swap
			else {
				return elementary;
			}
			
		} // End of getting inverse of an elementary matrix
		
		// If object is not any type of matrix
		if (!(matrix instanceof double[][])) {
			throw new IllegalArgumentException("Parameter not a matrix!");
		}
		
		// Converting matrix to usable array
		double[][] actualArray = (double[][])matrix;
		
		// If matrix is 1x1
		if (actualArray.length == 1) {
			actualArray[0][0] = 1 / actualArray[0][0];
			return actualArray;
		}
		
		// Determines if matrix of size 3x3 or less is even invertible
		// If determinant = 0, then matrix is not invertible
		if (actualArray.length < 4 && Determinant.getDeterminant(matrix) == 0) {
			System.out.println("Sorry, this matrix is not invertible!\n");
			return -1;
		}
		// If matrix is invertible, then it finds the inverse by
		// adjoining the identity matrix to it and performing the
		// Gauss-Jordan algorithm
		else {
			
			// New array for both initial array and identity matrix
			double[][] newArray = new double[actualArray.length][actualArray.length * 2];
			// Tracks when the identity matrix's elements should be filled in
			int identityCol = 0;
			// Fills in values for newArray
			for (int i = 0; i < newArray.length; i++) {
				
				for (int j = 0; j < newArray[0].length; j++) {
					
					// If iterating through columns meant for initial array
					if (j < actualArray.length) {
						newArray[i][j] = actualArray[i][j];
					}
					// If iterating through columns meant for identity matrix
					else {
						
						// In an identity matrix, only the main diagonal elements
						// are 1s.
						if (i == identityCol) {
							newArray[i][identityCol + actualArray.length] = 1.0;
							++identityCol;
						}
						// The rest of the elements are all 0s.
						else {
							break;
						}
						
					}
					
				} // End of iterating through columns
			} // End of iterating through rows
			
			// Saving space by reusing newArray for RRE matrix
			newArray = GaussJordan.reduceToRRE(newArray, 2);
			
			// Determines if matrix is invertible for
			// 4x4 or greater size matrices
			if (newArray[newArray.length - 1][actualArray.length - 1] == 0.0) {
				System.out.println("Sorry, this matrix is not invertible!");
				return -1;
			}
			
			// Saving space by reusing actualArray for inverted matrix
			for (int i = 0; i < actualArray.length; i++) {
				// Need only the elements from newArray where the identity
				// matrix originally was
				for (int j = actualArray.length; j < newArray[0].length; j++) {
					
					actualArray[i][j - actualArray.length] = Math.round(newArray[i][j] * 1000) / 1000.0;
					
				} // End of iterating through columns
			} // End of iterating through rows
			
			return actualArray;
			
		} // End of finding the inverse of the matrix
		
	} // End of getInverse method

} // End of class