import java.io.File;

public class Driver {
    public static void main(String[] args) throws Exception {
        Polynomial p = new Polynomial();

        double[] c1 = {6, 1, -4, 5};
        int[] e1 = {0, 5, 1, 2};

        Polynomial p1 = new Polynomial(c1,e1);

        double[] c2 = {-1, -2, 2, 1, -9};
        int[] e2 = {1, 8, 3, 8, 0};

        Polynomial p2 = new Polynomial(c2,e2);
        Polynomial s = p1.multiply(p2);

        System.out.println("s(0.1) = " + s.evaluate(0.1));
        if (s.hasRoot(1))
            System.out.println("1 is a root of s");
        else
            System.out.println("1 is not a root of s");

        File file = new File("test_in.txt");
        Polynomial p3 = new Polynomial(file);

        p2.saveToFile("test_out.txt");
        p3.saveToFile("test_out_2.txt");
    }
}