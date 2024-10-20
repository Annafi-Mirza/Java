package matrices;

// Remove 1st line & import into IDE
// only if you want to use file directly

// For filling in a square matrix
import java.util.Scanner;

public class Matrix {
	
	// INSTANTIATION
	
	// Instance variables
	private double[][] array;
	
	// RRE Array dimension variables
	private static final int ARRAY_ROWS = 3;
	private static final int ARRAY_COLUMNS = 4;
	
	// Constructors
	
	public Matrix(int sideLength) {
		GaussJordan.RREsteps.clear();
		array = new double[sideLength][sideLength];
		fillValues();
	}

	public Matrix(String line1, String line2, String line3) {
		GaussJordan.RREsteps.clear();
		array = translate(line1, line2, line3);
	}
	
	// TO STRING
	
	// Prints out a matrix
	public String toString() {
		String finalLine = "";
		for (int i = 0; i < array.length; i++) {
			for (int j = 0; j < array[0].length; j++) {
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
		condensed = condensed.toLowerCase();
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
			condensed = condensed.substring(0, operandIndex) +
					    "+0y" + condensed.substring(operandIndex);
			// ^ Adds y variable to middle of string
		}
		if (!zPresent) { // If z is not present
			int operandIndex = 0;
			for (int i = 0; i < condensed.length(); i++) {
				if ( (condensed.substring(i, i+1).equals("=")) ) {
					operandIndex = i;
					break;
				}
			} // ^ Finds index of = sign
			condensed = condensed.substring(0, operandIndex) +
					"+0z" + condensed.substring(operandIndex);
			// Adds z variable to middle of string
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
	
	// GAUSS-JORDAN ELIMINIATION
	
	public void getRRE() {
		GaussJordan.reduceToRRE(array, 1);
	} // End of getRRE method
	
	public void getRRESolutions() {
		GaussJordan.analyzeSolutions(array);
	} // End of getRRESolutions method
	
	// Makes matrix look nicer by converting 
	// -0.0 to 0.0 and rounding elements to 3
	// decimal places
	public void cleanMatrix() {
		
		for (int i = 0; i < array.length; i++) {
			for (int j = 0; j < array[0].length; j++) {
				
				// Changes any -0.0 to 0.0
				if (array[i][j] == 0.0) {
					array[i][j] = 0.0;
				}
				// Rounds any other element to
				// 3 decimal places
				else {
					array[i][j] = Math.round(array[i][j] * 1000) / 1000.0;
				}
				
			} // End of iterating through columns
		} // End of iterating through rows
		
	} // End of cleanMatrix method
	
	// CREATING SQUARE MATRIX
	
	private void fillValues() {
		
		System.out.println("\nEnter the values for each row.\n"
				+ "For example, '1, 2, 3'\n");
		
		// Tracks user input
		Scanner in = new Scanner(System.in);
		String userValuesString;
		
		for (int i = 0; i < array.length; i++) {
			
			// Tracks user's numerical values
			double[] userValues = new double[array[0].length];
			
			System.out.print("Enter all " + array[0].length + " values for row " +
			(i + 1) + ".\nPut a comma between each value: ");
			
			// Ensures that user enters proper input
			while (true) {
				
				// Records user input
				userValuesString = in.next() + in.nextLine();
				// Removes any spaces from user input
				userValuesString = userValuesString.replaceAll(" ", "");
				
				// Tracks number of commas in user input
				// which correlates to number of elements
				// in user input
				int amountOfCommas = userValuesString.length() - 
						userValuesString.replaceAll(",", "").length();
				
				// If the user did not input correct number of elements
				if ( (array[0].length - 1) != (amountOfCommas) ) {
					System.err.println("Not the correct amount of values!");
				}
				// If the user entered correct number of elements
				else {
					break;
				}
			}
			
			userValuesString += ",";
			
			// Tracks beginning of each element in user input
			int beginningIndex = 0;
			// Tracks position in array containing user elements
			int arrayIndex = 0;
			// Adds user elements to array
			for (int index = 0; index < userValuesString.length(); index++) {
				// When an element is found by detecting
				// its corresponding comma
				if (userValuesString.charAt(index) == ',') {
					// Captures user element from larger string
					String userValueString = userValuesString.substring(beginningIndex, index);
					// Normally converts user element to double
					try {
						userValues[arrayIndex] = Double.parseDouble(userValueString);
					}
					// If user wrote letters instead of numbers,
					// element is set to 0
					catch(Exception e) {
						userValues[arrayIndex] = 0.0;
					}
					// Next array position is to be filled
					++arrayIndex;
					// beginningIndex starts from next element
					beginningIndex = index + 1;
				}
			}
			
			// Inserts each element in each position along row
			for (int j = 0; j < array[0].length; j++) {
				
				array[i][j] = userValues[j];
				
			} // End of iterating through columns
			
		} // End of iterating through rows
		
		System.out.println();
		
	} // End of fillValues method
	
	// GETTING DETERMINANT
	
	public double determinant() {
		return Determinant.getDeterminant(array);
	} // End of determinant method
	
	// GETTING INVERSE
	
	public Object inverse() {
		return Inverse.getInverse(array);
	} // End of inverse method

} // End of class