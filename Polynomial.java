import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Polynomial {
    private double[] coefficients;
    private int[] exponents;

    public Polynomial() {
        this.coefficients = new double[1];
        this.coefficients[0] = 0;

        this.exponents = new int[1];
        this.exponents[0] = 0;
    }

    public Polynomial(double[] coefficients, int[] exponents) {
        Pair optimized = optimizeExponents(coefficients, exponents);

        this.coefficients = optimized.first;
        this.exponents = optimized.second;
    }

    public Polynomial(File file) throws Exception {
            Scanner scanner = new Scanner(file);
            String line = scanner.nextLine();
            scanner.close();

            String[] terms = line.split("(?=[+-])");

            double[] coefficients = new double[terms.length];
            int[] exponents = new int[terms.length];

            for (int i = 0; i < terms.length; i++) {
                String term = terms[i];

                if (term.contains("x")) {
                    String[] parts = term.split("x");

                    if (parts[0].isEmpty()) {
                        coefficients[i] = 1;
                    } else if (parts[0].equals("-")) {
                        coefficients[i] = -1;
                    } else {
                        coefficients[i] = Double.parseDouble(parts[0]);
                    }

                    if (parts.length == 1) {
                        exponents[i] = 1;
                    } else {
                        exponents[i] = Integer.parseInt(parts[1]);
                    }
                } else {
                    coefficients[i] = Double.parseDouble(term);
                    exponents[i] = 0;
                }
            }


            Pair optimized = optimizeExponents(coefficients, exponents);

            this.coefficients = optimized.first;
            this.exponents = optimized.second;
    }

    private Pair optimizeExponents(double[] coefficients, int[] exponents){
        for (int i = 0; i < exponents.length; i++) {
            for (int j = i + 1; j < exponents.length; j++) {

                if (exponents[i] < exponents[j]) {

                    int temp = exponents[i];
                    exponents[i] = exponents[j];
                    exponents[j] = temp;

                    double temp2;
                    temp2 = coefficients[i];

                    coefficients[i] = coefficients[j];
                    coefficients[j] = temp2;
                }

                if (exponents[i] == exponents[j]) {
                    coefficients[i] += coefficients[j];
                    coefficients[j] = 0;
                }
            }
        }


        int count = 0;
        for (int i = 0; i < exponents.length; i++) {
            if (coefficients[i] != 0) {
                count++;
            }
        }

        Pair result = new Pair(count);

        int k = 0;
        for (int i = 0; i < exponents.length; i++) {
            if (coefficients[i] != 0) {

                result.first[k] = coefficients[i];
                result.second[k] = exponents[i];
                k++;
            }
        }

        return result;
    }

    public Polynomial add(Polynomial polynomial) {
        double[] newCoefficients = new double[this.coefficients.length + polynomial.coefficients.length];
        int[] newExponents = new int[this.exponents.length + polynomial.exponents.length];

        int i = 0;
        int j = 0;
        int k = 0;

        while (i < this.exponents.length && j < polynomial.exponents.length) {
            if (this.exponents[i] > polynomial.exponents[j]) {

                newCoefficients[k] = this.coefficients[i];
                newExponents[k] = this.exponents[i];
                i++;

            } else if (this.exponents[i] < polynomial.exponents[j]) {

                newCoefficients[k] = polynomial.coefficients[j];
                newExponents[k] = polynomial.exponents[j];
                j++;

            } else {

                newCoefficients[k] = this.coefficients[i] + polynomial.coefficients[j];
                newExponents[k] = this.exponents[i];

                i++;
                j++;
            }

            k++;
        }

        while (i < this.exponents.length) {
            newCoefficients[k] = this.coefficients[i];
            newExponents[k] = this.exponents[i];

            i++;
            k++;
        }

        while (j < polynomial.exponents.length) {
            newCoefficients[k] = polynomial.coefficients[j];
            newExponents[k] = polynomial.exponents[j];

            j++;
            k++;
        }

        return new Polynomial(newCoefficients, newExponents);
    }



    public Polynomial multiply(Polynomial polynomial){
        double[] newCoefficients = new double[this.coefficients.length * polynomial.coefficients.length];
        int[] newExponents = new int[this.exponents.length * polynomial.exponents.length];

        int k = 0;

        for (int i = 0; i < this.exponents.length; i++) {
            for (int j = 0; j < polynomial.exponents.length; j++) {

                newCoefficients[k] = this.coefficients[i] * polynomial.coefficients[j];
                newExponents[k] = this.exponents[i] + polynomial.exponents[j];

                k++;
            }
        }

        return new Polynomial(newCoefficients, newExponents);
    }

    public double evaluate(double x) {
        double result = 0;

        for (int i = 0; i < this.coefficients.length; i++) {
            result += this.coefficients[i] * Math.pow(x, this.exponents[i]);
        }

        return result;
    }

    public boolean hasRoot(double x) {
        return this.evaluate(x) == 0;
    }

    public File saveToFile(String path) throws Exception{
        File file = new File(path);

        PrintWriter writer = new PrintWriter(file);
        String polynomialString = "";

        for (int i = 0; i < coefficients.length; i++) {
            if (coefficients[i] < 0) {
                polynomialString += coefficients[i];

            } else if (i != 0) {
                polynomialString += "+" + coefficients[i];

            } else {
                polynomialString += coefficients[i];
            }

            if (exponents[i] != 0) {
                polynomialString += "x" + exponents[i];
            }
        }

        writer.print(polynomialString);
        writer.close();

        return file;
    }
}
