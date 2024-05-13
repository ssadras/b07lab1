public class Polynomial {
    private double[] coefficients;

    public Polynomial() {
        this.coefficients = new double[1];
        this.coefficients[0] = 0;
    }

    public Polynomial(double[] coefficients) {
        this.coefficients = coefficients;
    }

    public Polynomial add(Polynomial polynomial) {
        int length = Math.max(this.coefficients.length, polynomial.coefficients.length);
        double[] result = new double[length];

        for (int i = 0; i < length; i++) {
            double a;

            if (i < this.coefficients.length)
                a = this.coefficients[i];
            else
                a = 0;

            double b;

            if (i < polynomial.coefficients.length)
                b = polynomial.coefficients[i];
            else
                b = 0;

            result[i] = a + b;
        }

        return new Polynomial(result);
    }

    public double evaluate(double x) {
        double result = 0;

        for (int i = 0; i < this.coefficients.length; i++) {
            result += this.coefficients[i] * Math.pow(x, i);
        }

        return result;
    }

    public boolean hasRoot(double x) {
        return this.evaluate(x) == 0;
    }
}
