import javax.swing.*;
import java.time.LocalDate;

/**
 * 泛型方法
 *
 * @version 1.0.0 2020-02-05
 * @author bruce
 */
public class PairTest {

    public static void main(String[] args) {

        LocalDate[] birthdays = {
                LocalDate.of(1994,11,11),
                LocalDate.of(1937,3,1),
                LocalDate.of(1970,1,23),
                LocalDate.of(1933,9,3)
        };

        Pair<LocalDate> mm = ArrayAlg.minmax(birthdays);
        System.out.println("min=" + mm.getFirst());
        System.out.println("max=" + mm.getSecond());

    }

}

class ArrayAlg {

    public static <T extends Comparable> Pair<T> minmax(T[] a) {
        if (a == null || a.length == 0) return null;
        T min = a[0];
        T max = a[0];
        for (int i = 1; i < a.length; i++) {
            if (min.compareTo(a[i]) > 0) min = a[i];
            if (max.compareTo(a[i]) < 0) max = a[i];
        }
        return new Pair<>(min, max);
    }

}

class Pair<T> {

    private T first;
    private T second;

    public Pair() {
        first = null;
        second = null;
    }

    public Pair(T first, T second) {
        this.first = first;
        this.second = second;
    }

    public T getFirst() { return first; }
    public T getSecond() { return second; }

    public void setFirst(T newValue) {
        first = newValue;
    }

    public void setSecond(T newValue) {
        second = newValue;
    }
}