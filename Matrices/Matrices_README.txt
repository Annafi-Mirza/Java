Hello!

This Java project of mine all deals with everything to do
with matrices with 3 rows and 4 columns because they will
be used to solve systems of linear equations.

I got the inspiration for this from my linear algebra
class.

In the base Matrix file, you can translate 3 String
representations of equations (x + y + z = 1, for instance),
or basically a system of 3 equations, into an augmented
matrix. This does have the limitation of not being able to
directly input equations like "x + z = 2" into it, but you
can input "x + 0y + z = 2" instead.

In the MatrixTester file, you can utilize this Matrix file
along with all other Matrix-related files through
interacting with the console, where you can create and
manipulate a matrix.

Assume that all entries within this matrix are in a field Q.

Along with these, there are other files outlining different
matrix functions, including:

- The GaussJordan file, which allows you to reduce your
matrix to RRE form and determine the solutions, if any, to
your system of equations. (W.I.P.)

- The MatrixInverse file, which allows you to find the
inverse of a matrix (if possible) with the
Gauss-Jordan formula. (W.I.P.)

- The MatrixDeterminant file, which allows you to find the
determinant of a matrix through cofactor expansion for
matrices of size 3x3 or less, or multiplying the
determinant of each elementary matrice needed to find the
inverse of the matrix when it is greater than 3x3. (W.I.P.)