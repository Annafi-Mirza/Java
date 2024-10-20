package matrices;

//Remove 1st line & import into IDE
//only if you want to use file directly

import java.util.Scanner;

public class MatrixTester {
	
	static Scanner in = new Scanner(System.in); // Scans user input
	static Matrix matrixA; // User's matrix
	
	// MAKING MATRICES
	
	// If the user wants to create a square matrix for determinants
	public static void createSquareMatrix() {
		
		// Loops until user picks a numerical side length
		int sideLength = 0;
		boolean enteredProperly = false;
		while (!enteredProperly) {
			System.out.print("\nWhat should each side length be? ");
			String sideLengthString = in.next();
			try {
				sideLength = Integer.parseInt(sideLengthString);
				enteredProperly = true;
			}
			catch(Exception e) {
				System.err.println("Invalid input!");
				sideLengthString = in.next();
			}
		}
		
		matrixA = new Matrix(sideLength);
		System.out.println(matrixA);
		
	} // End of createSquareMatrix method
	
	// If the user wants to create an augmented matrix
	public static void createAugmentedMatrix() {
		
		System.out.println("\nEnter 3 equations to form a system of\n"
				+ 			"equations. Acceptable forms include:\n"
				+ 			"3x - 4.6y + 234z = -339\n"
				+ 			"-23x + 4z = 3\n"
				+			"-y = -1"
				+ 			"0x + y + 0z = 7\n"
				+ 			"x-y+2z=4\n");
		
		String line1 = "";
		String line2 = "";
		String line3 = "";
		
		System.out.print("Equation 1: ");
		line1 += in.next() + in.nextLine();
		System.out.println("Equation 1: " + line1 + "\n");
		
		System.out.print("Equation 2: ");
		line2 += in.next() + in.nextLine();
		System.out.println("Equation 2: " + line2 + "\n");
		
		System.out.print("Equation 3: ");
		line3 += in.next() + in.nextLine();
		System.out.println("Equation 3: " + line3 + "\n");
		
		matrixA = new Matrix(line1, line2, line3);
		System.out.println("\n\nCool, here's your matrix!\n" + matrixA);
	
	} // End of createAugmentedMatrix method
	
	// MATRIX FUNCTIONS
	
	// Gets RRE form of matrix (solutions to system of equations) if any
	public static void getRREForm() {
		System.out.println("Loading RRE form...\n");
		matrixA.getRRE();
		matrixA.cleanMatrix();
		System.out.println("Reduced Row Echelon Form: ");
		System.out.println(matrixA);
		matrixA.getRRESolutions();
	} // End of getRREForm method
	
	// Gets inverse of matrix if possible
	public static void getInverse() {
		System.out.println("Loading inverse...\n");
		Object inverseMatrixObject = matrixA.inverse();
		if (!(inverseMatrixObject instanceof Integer)) {
			double[][] inverseMatrix = (double[][])inverseMatrixObject;
			
			// Prints out the inverse matrix
			String finalLine = "";
			for (int i = 0; i < inverseMatrix.length; i++) {
				for (int j = 0; j < inverseMatrix[0].length; j++) {
					// Prints out each element of each row
					finalLine += inverseMatrix[i][j] + " ";
				}
				// Goes to next row
				finalLine += "\n";
			}
			System.out.println(finalLine);
		}
	} // End of getInverse method
	
	// Gets determinant of matrix
	public static void getDeterminant() {
		System.out.println("Loading determinant...");
		System.out.println("Your determinant: " + matrixA.determinant() + "\n");
	} // End of getDeterminant method
	
	// MAIN METHOD
	
	public static void main(String[] args) {
		
		System.out.println("Hello! Welcome to the world of matrices.");
		// ^ Introduction
		
		// Console program for all matrix operations
		boolean programOn = true;
		while (programOn) {
			
			String userMatrixChoice; // Keeps user's choice for what to do w/ matrix
			boolean properlyDone = false; // Tracks if user makes valid inputs
			
			// Allows user to exit program
			System.out.print("Do you want to continue or quit? (1/2) ");
			String continueKey = in.next();
			while (!properlyDone) {
				switch (continueKey) {
					case "1":
						properlyDone = true;
						break;
					case "2":
						programOn = false;
						System.out.println("See you soon!");
						properlyDone = true;
						break;
					default:
						System.err.println("Not a valid option!");
						continueKey = in.next();
				}
			} // End of prompting user to continue program
			
			if (!programOn) {
				break;
			} // ^ Checks if user answered "2" to question above
			
			// Prompts the user to create a matrix
			System.out.print("\nFirst, do you want to create a square\n"
					+ 			"matrix or an augmented matrix? (1/2) ");
			userMatrixChoice = in.next();
			properlyDone = false;
			while (!properlyDone) {
				switch(userMatrixChoice) {
					case "1":
						createSquareMatrix();
						properlyDone = true;
						break;
					case "2":
						createAugmentedMatrix();
						properlyDone = true;
						break;
					default:
						System.err.println("Not a valid option!");
						userMatrixChoice = in.next();
				}
			} // End of creating matrix
			
			// Prompts user to manipulate matrix in different ways
			properlyDone = false;
			// If user made a square matrix
			if (userMatrixChoice.equals("1")) {
				System.out.println("Now that you have your square matrix,\n"
						+ 		   "what do you want to do with it? (1-2)\n"
						+ 		   "(1) > Find inverse of matrix if possible.\n"
						+ 		   "(2) > Find determinant of matrix.");
				userMatrixChoice = in.next();
				while (!properlyDone) {
					switch(userMatrixChoice) {
						case "1":
							getInverse();
							properlyDone = true;
							break;
						case "2":
							getDeterminant();
							properlyDone = true;
							break;
						default:
							System.err.println("Not a valid option!");
							userMatrixChoice = in.next();
					}
				} // End of manipulating square matrix
			}
			// If user made an augmented matrix
			else {
				System.out.print("Do you want to reduce your matrix to RRE form\n"
						+ 		 "and, thus, gain the solutions (if any) to your\n"
						+ 		 "system? (Yes/No, 1/2) ");
				userMatrixChoice = in.next();
				while (!properlyDone) {
					switch(userMatrixChoice) {
					case "1":
						getRREForm();
						properlyDone = true;
						break;
					case "2":
						System.out.println("Okay!\n");
						properlyDone = true;
						break;
					default:
						System.err.println("Not a valid option!");
						userMatrixChoice = in.next();
					}
				}
			}
			// End of user manipulation of matrix
			
		} // End of console program (while loop)
	
	} // End of main method

} // End of class