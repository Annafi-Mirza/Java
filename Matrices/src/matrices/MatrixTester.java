package matrices;

//Remove 1st line & import into IDE
//only if you want to use file directly

import java.util.Scanner;

public class MatrixTester {
	
	public static void main(String[] args) {
	
		Scanner in = new Scanner(System.in); // Scans user input
		System.out.println("Hello! Welcome to the world of matrices.\n"
				+ 			"Enter 3 equations to form a system of\n"
				+ 			"equations. Acceptable forms include:\n"
				+ 			"x + 2y + 3z = 0\n"
				+ 			"-x + 0y - z = 2\n"
				+ 			"0x + y + 0z = 7\n"
				+ 			"x-y+2z=4\n");
		// ^ Introduction
		
		// Console program for all matrix operations
		while (true) {
			
			String line1 = "";
			String line2 = "";
			String line3 = "";
			
			System.out.print("Do you want to continue or quit? (1/2) ");
			String continueKey = in.next();
			if (continueKey.equals("2")) {
				System.out.print("See you soon!");
				break;
			}
			System.out.println("\nCool! Enter each line of your matrix -\n");
			
			System.out.print("Equation 1: ");
			line1 += in.next() + in.nextLine();
			System.out.println("Equation 1: " + line1 + "\n");
			
			System.out.print("Equation 2: ");
			line2 += in.next() + in.nextLine();
			System.out.println("Equation 2: " + line2 + "\n");
			
			System.out.print("Equation 3: ");
			line3 += in.next() + in.nextLine();
			System.out.println("Equation 3: " + line3 + "\n");
			
			Matrix matrixA = new Matrix(line1, line2, line3);
			System.out.println("\n\nCool, here's your matrix!\n" + matrixA);
			
		} // End of console program (while loop)
	
	} // End of main method

} // End of class