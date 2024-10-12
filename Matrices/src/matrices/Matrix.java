package matrices;

// Remove 1st line & import into IDE
// only if you want to use file directly

public class Matrix {
	
	// INSTANTIATION
	
	// Instance variables
	private double[][] array;
	
	// Array dimension variables
	private static final int ARRAY_ROWS = 3;
	private static final int ARRAY_COLUMNS = 4;
	
	// Constructors
	public Matrix(double[][] array) {
		this.array = array;
	}
	public Matrix(String line1, String line2, String line3) {
		array = translate(line1, line2, line3);
	}
	
	// TO STRING
	
	// Prints out a matrix
	public String toString() {
		String finalLine = "";
		for (int i = 0; i < ARRAY_ROWS; i++) {
			for (int j = 0; j < ARRAY_COLUMNS; j++) {
				// Prints out each element of each row
				finalLine += array[i][j] + " ";
			}
			// Goes to next row
			finalLine += "\n";
		}
		return finalLine;
	} // End of toString method
	
	// TRANSLATION
	
	// Translate sentences to 2d array
	private double[][] translate(String line1, String line2, String line3) {
		double[][] outputArray = new double[ARRAY_ROWS][ARRAY_COLUMNS];
		
		for (int i = 0; i < ARRAY_ROWS; i++) { // Completes each matrix row
			String coefficients = "";
			switch(i) { // Determine which row to fill
				case 0:
					coefficients = coefficientsOneLine(line1);
					break;
				case 1:
					coefficients = coefficientsOneLine(line2);
					break;
				case 2:
					coefficients = coefficientsOneLine(line3);
					break;
			}
			
			String coefficient = "";
			int coefficientIndex = 0; // Where coefficient begins
			int coefficientArrayIndex = 0; // Which A(i, j) in matrix A
				
			for (int a = 0; a < coefficients.length() - 2; a++) {
				boolean coefficientFound = false;
				
				// If current char is an operator except at beginning or immediately after = operator
				if ( (a != 0) && (!(coefficients.substring(a-1, a).equals("="))) && ((coefficients.substring(a, a+1).equals("-")) || (coefficients.substring(a, a+1).equals("+"))) ) {
					while (coefficientIndex != a) {
						coefficient += coefficients.substring(coefficientIndex, coefficientIndex+1);
						++coefficientIndex;
					}
					coefficientFound = true;
					// ^ Adds to coefficient every char after previous operator and before current one
				}
				// If current char is =, for operand before =
				else if ( coefficients.substring(a, a+1).equals("=") ) {
					while (coefficientIndex != a) {
						coefficient += coefficients.substring(coefficientIndex, coefficientIndex+1);
						++coefficientIndex;
					}
					coefficientFound = true;
					// ^ Adds to coefficient every char after previous operator and before current one
				}
				// If current char is =, for operand after =, also a != 0 so no IndexOutOfBoundsException
				else if ( (a != 0) && (coefficients.substring(a-1, a).equals("=")) ) {
					coefficientIndex = a;
					while (coefficientIndex != coefficients.length() - 2) {
						coefficient += coefficients.substring(coefficientIndex, coefficientIndex+1);
						++coefficientIndex;
					}
					coefficientFound = true;
					// ^ Adds to coefficient every char after previous operator and before current one
				}
				// If there exists an operand (coefficient), adds it to matrix and moves to next A(i, j)
				if (coefficientFound) {
					outputArray[i][coefficientArrayIndex] = Double.parseDouble(coefficient);
					++coefficientArrayIndex;
					coefficient = "";
				}
			} // End of coefficientLine iteration
			
		} // End of row iteration
		
		return outputArray;
	} // End of translate method
	
	// Translate 1 user-generated sentence to usable sentence for matrix
	private String coefficientsOneLine(String line) {
		String newLine = ""; // Line to be returned
		String condensed = line.replaceAll(" ", "") + " ";
		// ^ Line w/o spaces & extra space at end to prevent bound exceptions
		
		int prevVarIndex = 0; // Keep track of last variable
		boolean xPresent = false; // Sees if x is present or not
		for (int i = 0; i < condensed.length() - 1; i++) {
			String substring = condensed.substring(i, i + 1); // Each char
			
			switch(substring) { // Switch-case detecting variables
				case "x":
					if (i == 0) { // No coefficients
						newLine += "1";
					}
					else if (i == 1) { // 0 or -1 as coefficient
						if ( condensed.substring(0, 1).equals("0") ) {
							newLine += "0";
						}
						else {
							newLine += "-1";
						}
					}
					else { // Any other coefficient
						int beginning = 0;
						while (beginning != i) {
							newLine += condensed.substring(beginning, beginning+1);
							++beginning;
						}
					}
					prevVarIndex = i; // Index of x
					xPresent = true;
					break;
					
				case "y":
					int beginning = prevVarIndex + 1;
					if ( condensed.substring(beginning+1, beginning+2).equals("y") ) {
						// ^ 1 or -1 as coefficient
						newLine += condensed.substring(beginning, beginning+1) + "1";
					}
					else { // Any other coefficient
						while (beginning != i) {
							newLine += condensed.substring(beginning, beginning+1);
							++beginning;
						}
					}
					prevVarIndex = i; // Index of y
					
					/** (W.I.P.)
					// In case x is not present:
					if (!(xPresent)) {
						// Inserts 0 for x at beginning of newLine
						StringBuilder str = new StringBuilder(newLine);
						str.insert(0, '0');
						if ( !(newLine.substring(0, 1).equals("-")) ) {
							str.insert(1, '+');
						}
 						newLine = str.toString();
					}
					**/
					
					break;
					
				case "z":
					int begining = prevVarIndex + 1;
					if ( condensed.substring(begining+1, begining+2).equals("z") ) {
						// ^ 1 or -1 as coefficient
						newLine += condensed.substring(begining, begining+1) + "1";
					}
					else { // Any other coefficient
						while (begining != i) {
							newLine += condensed.substring(begining, begining+1);
							++begining;
						}
					}
					break;
					
				case "=":
					newLine += "=";
					while (i != condensed.length() - 1) {
						i++;
						newLine += condensed.substring(i, i+1);
						// ^ Constant that expression is equal to
					}
					i = condensed.length(); // Ends for-loop
					break;
			} // End of switch-case
			
		} // End of for-loop
		
		newLine += " ";
		return newLine;
	} // End of translateOneLine

} // End of class