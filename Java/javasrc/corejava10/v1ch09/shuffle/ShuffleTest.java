import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * the random shuffle and sort algorithms
 *
 * @version 1.0.0 2020-02-18
 * @author bruce
 */
public class ShuffleTest {

    public static void main(String[] args) {
        List<Integer> numbers = new ArrayList<>();
        for (int i = 1; i <= 49; i++) {
            numbers.add(i);
        }
        Collections.shuffle(numbers);
        List<Integer> winningCombination = numbers.subList(0, 6);
        Collections.sort(winningCombination);
        System.out.println(winningCombination);
    }
}
