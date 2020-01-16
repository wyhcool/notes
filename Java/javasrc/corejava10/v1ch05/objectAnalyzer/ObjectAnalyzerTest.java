import java.util.ArrayList;

/**
 * use reflection to spy on objects
 *
 * @version 1.0.0 2020-01-16
 * @author bruce
 */
public class ObjectAnalyzerTest {

    public static void main(String[] args) {

        ArrayList<Integer> squares = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            squares.add(i * i);
        }
        System.out.println(new ObjectAnalyzer().toString(squares));
    }
}
