package Lab2;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;
class InvalidColumnNumberException extends Exception{
    public InvalidColumnNumberException(String message) {
        super(message);
    }
}
class InvalidRowNumberException extends Exception{
    public InvalidRowNumberException(String message) {
        super(message);
    }
}

class MatrixReader {
    public static DoubleMatrix read(InputStream input) throws InsufficientElementsException {
        Scanner scanner = new Scanner(input);

        int m = scanner.nextInt();
        int n = scanner.nextInt();
        double []niza = new double[m*n];
        for (int i = 0; i < m*n; i++) {
            niza[i] = scanner.nextDouble();
        }

        return new DoubleMatrix(niza, m,n);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}

class InsufficientElementsException extends Exception{
    public InsufficientElementsException(String message) {
        super(message);
    }
}
final  class DoubleMatrix {

    double[] a;
    int m;
    int n;
    double[][] matrix;

    public DoubleMatrix(double[] a, int m, int n) throws InsufficientElementsException {
        this.a = a;
        this.m = m;
        this.n = n;

        if (a.length < m * n) {
            throw new InsufficientElementsException("Insufficient number of elements");
        } else if (a.length > m * n) {
            matrix = new double[m][n];
            int temp = a.length - m * n;

            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    this.matrix[i][j] = a[temp];
                    temp++;
                }
            }
        } else {
            matrix = new double[m][n];
            int temp = 0;

            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    this.matrix[i][j] = a[temp];
                    temp++;
                }
            }
        }
    }

    public String getDimensions() {
        return "[" + m + " x " + n + "]";
    }

    public int rows() {
        return m;
    }

    public int columns() {
        return n;
    }

    public double maxElementAtRow(int row) throws InvalidRowNumberException {
        if (row > m || row < 1) {
            throw new InvalidRowNumberException("Invalid row number");
        }

        double max = matrix[row-1][0];
        for (int i = 0; i < n; i++) {
            if (max <= matrix[row-1][i]) {
                max = matrix[row-1][i];
            }
        }
        return max;
    }




    public double maxElementAtColumn(int column) throws InvalidColumnNumberException {
        if (column > n || column < 1) {
            throw new InvalidColumnNumberException("Invalid column number");
        }

        double max = matrix[0][column-1];
        for (int i = 1; i < m; i++) {
            if (max <= matrix[i][column-1]) {
                max = matrix[i][column-1];
            }
        }
        return max;
    }

    public double sum() {
        double suma = 0.0;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                suma += matrix[i][j];
            }
        }
        return suma;
    }

    public double[] toSortedArray() {
        double[] a = new double[m * n];
        int temp = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                a[temp++] = matrix[i][j];
            }
        }

        Arrays.sort(a);

        for (int i = 0; i < a.length / 2; i++) {
            double temp2 = a[i];
            a[i] = a[a.length - 1 - i];
            a[a.length - 1 - i] = temp2;
        }
        return a;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DoubleMatrix that = (DoubleMatrix) o;
        return m == that.m && n == that.n && Arrays.deepEquals(matrix, that.matrix);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(m, n);
        result = 31 * result + Arrays.deepHashCode(matrix);
        return result;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                sb.append(String.format("%.2f", matrix[i][j]));
                if (j != n - 1) {
                    sb.append("\t");
                }
            }
            if (i != m - 1)
                sb.append("\n");
        }
        return sb.toString();
    }
}

public class DoubleMatrixTester {

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        int tests = scanner.nextInt();
        DoubleMatrix fm = null;

        double[] info = null;

        DecimalFormat format = new DecimalFormat("0.00");

        for (int t = 0; t < tests; t++) {

            String operation = scanner.next();

            switch (operation) {
                case "READ": {
                    int N = scanner.nextInt();
                    int R = scanner.nextInt();
                    int C = scanner.nextInt();

                    double[] f = new double[N];

                    for (int i = 0; i < f.length; i++)
                        f[i] = scanner.nextDouble();

                    try {
                        fm = new DoubleMatrix(f, R, C);
                        info = Arrays.copyOf(f, f.length);

                    } catch (InsufficientElementsException e) {
                        System.out.println("Exception caught: " + e.getMessage());
                    }

                    break;
                }

                case "INPUT_TEST": {
                    int R = scanner.nextInt();
                    int C = scanner.nextInt();

                    StringBuilder sb = new StringBuilder();

                    sb.append(R + " " + C + "\n");

                    scanner.nextLine();

                    for (int i = 0; i < R; i++)
                        sb.append(scanner.nextLine() + "\n");

                    fm = MatrixReader.read(new ByteArrayInputStream(sb
                            .toString().getBytes()));

                    info = new double[R * C];
                    Scanner tempScanner = new Scanner(new ByteArrayInputStream(sb
                            .toString().getBytes()));
                    tempScanner.nextDouble();
                    tempScanner.nextDouble();
                    for (int z = 0; z < R * C; z++) {
                        info[z] = tempScanner.nextDouble();
                    }

                    tempScanner.close();

                    break;
                }

                case "PRINT": {
                    System.out.println(fm.toString());
                    break;
                }

                case "DIMENSION": {
                    System.out.println("Dimensions: " + fm.getDimensions());
                    break;
                }

                case "COUNT_ROWS": {
                    System.out.println("Rows: " + fm.rows());
                    break;
                }

                case "COUNT_COLUMNS": {
                    System.out.println("Columns: " + fm.columns());
                    break;
                }

                case "MAX_IN_ROW": {
                    int row = scanner.nextInt();
                    try {
                        System.out.println("Max in row: "
                                + format.format(fm.maxElementAtRow(row)));
                    } catch (InvalidRowNumberException e) {
                        System.out.println("Exception caught: " + e.getMessage());
                    }
                    break;
                }

                case "MAX_IN_COLUMN": {
                    int col = scanner.nextInt();
                    try {
                        System.out.println("Max in column: "
                                + format.format(fm.maxElementAtColumn(col)));
                    } catch (InvalidColumnNumberException e) {
                        System.out.println("Exception caught: " + e.getMessage());
                    }
                    break;
                }

                case "SUM": {
                    System.out.println("Sum: " + format.format(fm.sum()));
                    break;
                }

                case "CHECK_EQUALS": {
                    int val = scanner.nextInt();

                    int maxOps = val % 7;

                    for (int z = 0; z < maxOps; z++) {
                        double work[] = Arrays.copyOf(info, info.length);

                        int e1 = (31 * z + 7 * val + 3 * maxOps) % info.length;
                        int e2 = (17 * z + 3 * val + 7 * maxOps) % info.length;

                        if (e1 > e2) {
                            double temp = work[e1];
                            work[e1] = work[e2];
                            work[e2] = temp;
                        }

                        DoubleMatrix f1 = fm;
                        DoubleMatrix f2 = new DoubleMatrix(work, fm.rows(),
                                fm.columns());
                        System.out
                                .println("Equals check 1: "
                                        + f1.equals(f2)
                                        + " "
                                        + f2.equals(f1)
                                        + " "
                                        + (f1.hashCode() == f2.hashCode() && f1
                                        .equals(f2)));
                    }

                    if (maxOps % 2 == 0) {
                        DoubleMatrix f1 = fm;
                        DoubleMatrix f2 = new DoubleMatrix(new double[]{3.0, 5.0,
                                7.5}, 1, 1);

                        System.out
                                .println("Equals check 2: "
                                        + f1.equals(f2)
                                        + " "
                                        + f2.equals(f1)
                                        + " "
                                        + (f1.hashCode() == f2.hashCode() && f1
                                        .equals(f2)));
                    }

                    break;
                }

                case "SORTED_ARRAY": {
                    double[] arr = fm.toSortedArray();

                    String arrayString = "[";

                    if (arr.length > 0)
                        arrayString += format.format(arr[0]) + "";

                    for (int i = 1; i < arr.length; i++)
                        arrayString += ", " + format.format(arr[i]);

                    arrayString += "]";

                    System.out.println("Sorted array: " + arrayString);
                    break;
                }

            }

        }

        scanner.close();
    }
}