import java.awt.*;

/**
 * list fonts on your pc
 *
 * @version 1.0.0 2020-02-27
 * @author bruce
 */
public class ListFonts {

    public static void main(String[] args) {

        String[] fontNames = GraphicsEnvironment
                .getLocalGraphicsEnvironment()
                .getAvailableFontFamilyNames();
        for (String fontName : fontNames) {
            System.out.println(fontName);
        }
    }
}
