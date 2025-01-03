Hello!

This Java project of mine all deals with everything to do
with matrices with 3 rows and 4 columns because they will
be used to solve systems of linear equations.

I got the inspiration for this from my linear algebra
class.

In the base Matrix file, you can translate 3 String
representations of equations (x + y + z = 1, for instance),
or basically a system of 3 equations, into an augmented
matrix.

In the MatrixTester file, you can utilize this Matrix file
along with all other Matrix-related files through
interacting with the console, where you can create and
manipulate a matrix.

Assume that all entries within this matrix are in a field Q.

Along with these, there are other files outlining different
matrix functions, including:

- The GaussJordan file, which allows you to reduce your
matrix to RRE form and determine the solutions, if any, to
your system of equations.

- The MatrixInverse file, which allows you to find the
inverse of a matrix (if possible) with the
Gauss-Jordan formula.

- The MatrixDeterminant file, which allows you to find the
determinant of a matrix through cofactor expansion for
matrices of size 3x3 or less, or multiplying the
determinant of each elementary matrice needed to find the
inverse of the matrix when it is greater than 3x3.

- The ElementaryMatrix file contains the elementary
matrices that track each change made to a matrix during
Gauss-Jordan elimination. This file is needed for the
GaussJordan file to run and also is important for when
calculating the determinant of matrices greater than 3x3.

- Lastly, the Rank file allows you to determine the linear
independence or dependence of any number of column vectors
of any size through Gauss-Jordan elimination and the counting
of pivot columns. These column vectors form a matrix that can
be used to find its rank and nullity, too.

** FOR ALL MY JAVA PROJECTS **

I use the Eclipse IDE to create all these, meaning that each
project comes in its own Java project, including a package.
To utilize these files, I suggest directly downloading the
Java files from Matrices/src/matrices, removing the package
statement at the top of each Java file, and importing them
or copying/pasting them into whatever IDE you use.
