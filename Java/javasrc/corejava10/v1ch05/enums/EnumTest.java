/**
 * enum
 *
 * @version 1.0.0 2020-01-12
 * @author bruce
 */
public class EnumTest {

    public static void main(String[] args) {
        System.out.println(Size.SMALL.toString());

        Size s = Enum.valueOf(Size.class, "LARGE");
        System.out.println(s);

        Size[] values = Size.values();
        for (Size ss : values) {
            System.out.println(ss);
        }

        System.out.println(Size.LARGE.ordinal()); //2
    }
}

enum Size {
    SMALL("S"), MEDIUM("M"), LARGE("L"), EXTRA_LARGE("XL");

    private String abbreviation;

    private Size(String abbreviation) { this.abbreviation = abbreviation; }
    public String getAbbreviation() { return abbreviation; }
}
