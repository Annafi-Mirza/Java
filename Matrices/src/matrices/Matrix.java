package matrices;

// Remove 1st line & import into IDE
// only if you want to use file directly

import java.util.ArrayList; // Used for inserting elements in middle of strings

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
				
			for (int a = 0; a <= coefficients.length() - 2; a++) {
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
					while (coefficientIndex != coefficients.length() - 1) {
						coefficient += coefficients.substring(coefficientIndex, coefficientIndex+1);
						++coefficientIndex;
					}
					coefficientFound = true;
					// ^ Adds to coefficient every char after previous operator and before current one
				}
				// If there exists an operand (coefficient), adds it to matrix and moves to next A(i, j)
				if (coefficientFound) {
					coefficient = coefficient.replaceAll(" ", "");
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
		
		boolean xPresent = false; // Sees if x is present or not
		boolean yPresent = false; // Sees if y is present or not
		boolean zPresent = false; // Sees if z is present or not
		for (int i = 0; i < condensed.length() - 1; i++) {
			String substring = condensed.substring(i, i+1);
			switch (substring) {
				case "x":
					xPresent = true;
					break;
				case "y":
					yPresent = true;
					break;
				case "z":
					zPresent = true;
					break;
			}
		} // ^ Iterates to see which variables are present or not
		
		if (!xPresent && !yPresent) { // If equation is z = n
			if ( condensed.substring(0, 1).equals("-") ) {
				condensed = "0x+0y" + condensed;
			} // ^ Adds other variables w/o new operator b/c there already is one
			else {
				condensed = "0x+0y+" + condensed;
			} // ^ Adds other variables w/ + operator
			xPresent = true;
			yPresent = true;
			// ^ All variables are now present; skips to translation
		}
		else if (!xPresent && !zPresent) { // If equation is y = n
			if ( condensed.substring(0, 1).equals("-") ) {
				condensed = "0x" + condensed;
			} // ^ Adds x variable w/o new operator b/c there already is one
			else {
				condensed = "0x+" + condensed;
			} // ^ Adds x variable w/ + operator
			xPresent = true;
			// ^ x and y variables are present. z is not & will be added later
		}
		else if (!yPresent && !zPresent) { // If equation is x = n
			int variableIndex = 0;
			for (int i = 0; i < condensed.length() - 1; i++) {
				if ( condensed.substring(i, i+1).equals("x") ) {
					variableIndex = i;
					break;
				}
			} // ^ Discovers index of x variable
			condensed = condensed.substring(0, variableIndex+1) +
						"+0y+0z" +
						condensed.substring(variableIndex+1);
			// ^ New string is everything up to x, the new y & z, and everything after = sign 
			yPresent = true;
			zPresent = true;
			// ^ All variables are now present; skips to translation
		}
		
		if (!xPresent) { // If x is not present
			if ( condensed.substring(0, 1).equals("-") ) {
				condensed = "0x" + condensed;
			}
			else {
				condensed = "0x+" + condensed;
			}
			// ^ Adds x variable to beginning of string
			condensed = condensed.substring(0, condensed.length() - 1);
			// ^ Removes any extra space at end of string
		}
		if (!yPresent) { // If y is not present
			int operandIndex = 0;
			for (int i = 0; i < condensed.length(); i++) {
				if ( (i != 0) && ((condensed.substring(i, i+1).equals("+")) || (condensed.substring(i, i+1).equals("-"))) ) {
					operandIndex = i;
					break;
				}
			} // ^ Finds index of 1st operator not at immediate beginning of string
			ArrayList<String> newLineArray = new ArrayList<String>();
			for (int j = 0; j < condensed.length() - 1; j++) {
				newLineArray.add( condensed.substring(j, j+1) );
			}
			newLineArray.add(operandIndex, "+0y");
			condensed = "";
			for (String element : newLineArray) {
				condensed += element;
			}
			// ^ Converts string to array, adds y to middle of array, 
			// & converts back to string
		}
		if (!zPresent) { // If z is not present
			int operandIndex = 0;
			for (int i = 0; i < condensed.length(); i++) {
				if ( (condensed.substring(i, i+1).equals("=")) ) {
					operandIndex = i;
					break;
				}
			} // ^ Finds index of = sign
			ArrayList<String> newLineArray = new ArrayList<String>();
			for (int j = 0; j < condensed.length() - 1; j++) {
				newLineArray.add( condensed.substring(j, j+1) );
			}
			newLineArray.add(operandIndex, "+0z");
			condensed = "";
			for (String element : newLineArray) {
				condensed += element;
			}
			// ^ Converts string to array, adds z to middle of array, 
			// & converts back to string
		}
		
		int prevVarIndex = 0; // Keep track of last variable
		for (int i = 0; i < condensed.length() - 1; i++) {
			String substring = condensed.substring(i, i + 1); // Each char
			
			switch(substring) { // Switch-case detecting variables
				case "x":
					if (i == 0) { // No coefficients
						newLine += "1";
					}
					else if (i == 1) { // 0, -1, or single-digit integer as coefficient
						if ( condensed.substring(0, 1).equals("0") ) {
							newLine += "0";
						}
						else if ( condensed.substring(0, 1).equals("-") ) {
							newLine += "-1";
						}
						else {
							newLine += condensed.substring(0, 1);
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