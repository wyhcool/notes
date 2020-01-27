import java.util.Arrays;
import java.util.Date;

/**
 * 计数器
 *
 * @version 1.0.0 2020-01-27
 * @author bruce
 */
public class CounterTest {

    public static void main(String[] args) {

        int[] counter = new int[1];
        Date[] dates = new Date[100];
        for (int i = 0; i < dates.length; i++) {
            dates[i] = new Date() {
                public int compareTo(Date other) {
                    counter[0]++;
                    return super.compareTo(other);
                }
            };
        }
        Arrays.sort(dates);
        System.out.println(Arrays.toString(counter));
    }
}
