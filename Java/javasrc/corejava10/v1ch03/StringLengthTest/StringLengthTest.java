/**
 * @version 1.0.1 2019-12-14
 * @author bruce
 */
public class StringLengthTest {

    public static void main(String[] args) {
        String t = "𡍄a";
        System.out.println(t.length()); //3

        System.out.println(t.codePointCount(0, t.length())); //2

        char first = t.charAt(0);
        char second = t.charAt(1);

        int index = t.offsetByCodePoints(0, 0);
        int cp = t.codePointAt(index);
        System.out.println(cp);

        int i = 0;

    }
}
