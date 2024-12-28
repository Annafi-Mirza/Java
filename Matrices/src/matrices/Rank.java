package matrices;

public class Rank {
	
	public static String[] getRank(double[][] inputArray) {
		
		// Array containing info for matrix like its rank
		String[] outputArray = new String[3];
		
		// Get num. of pivot col. by getting RRE form
		GaussJordan.reduceToRRE(inputArray, 1);
		
		// Counting num. of leading ones
		int counter = 0;
		for (int i = 0; i < inputArray.length; i++) {
			if (i >= inputArray[0].length) {
				break;
			}
			else if (inputArray[i][i] != 0){
				++counter;
			}
			else {
				// Check if any other number in row is non-zero
				for (int j = i; j < inputArray[0].length; j++) {
					if (inputArray[i][j] != 0) {
						++counter;
						break;
					}
				} // End of iterating through remaining row
			}
		} // End of iterating through diagonal elements
		
		// Determining linear independence
		if (counter != inputArray[0].length) {
			outputArray[0] = "The column vectors are not linearly independent.";
		}
		else {
			outputArray[0] = "The column vectors are linearly independent.";
		}
		
		// Determining the rank
		outputArray[1] = "The rank is " + counter + ".";
		
		// Determining the nullity
		outputArray[2] = "The nullity is " + (inputArray[0].length - counter) + ".";
		
		// Return array containing matrix info
		return outputArray;
		
	} // End of getRank method

} // End of class