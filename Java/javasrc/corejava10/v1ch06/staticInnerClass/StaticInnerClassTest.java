/**
 * 静态内部类
 *
 * @version 1.0.0 2020-01-27
 * @author bruce
 */
public class StaticInnerClassTest {

    public static void main(String[] args) {
        double[] d = new double[]{ 1.2, 3.4, 5.6, 7.8, 9.0 };
        ArrayAlg.Pair p = ArrayAlg.minmax(d);
        System.out.println("min=" + p.getFirst());
        System.out.println("max=" + p.getSecond());
    }
}


class ArrayAlg {

    public static class Pair {

        private double first;
        private double second;

        public Pair(double f, double s) {
            first = f;
            second = s;
        }

        public double getFirst() { return first; }
        public double getSecond() { return second; }

    }

    public static Pair minmax(double[] values) {
        double min = Double.POSITIVE_INFINITY;
        double max = Double.NEGATIVE_INFINITY;

        for (double v : values) {
            if (min > v) min = v;
            if (max < v) max = v;
        }

        return new Pair(min, max);
    }

}