public class Pair {
    public double[] first;
    public int[] second;

    public Pair(int count){
        this.first = new double[count];
        this.second = new int[count];
    }

    public Pair(double[] first, int[] second){
        this.first = first;
        this.second = second;
    }
}
