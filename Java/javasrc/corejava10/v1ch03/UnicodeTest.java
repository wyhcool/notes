/**
 * @version 1.0.1 2019-12-09
 * @author bruce
 */
public class UnicodeTest {

    //\u005B\u005D idea 不识别，报错 ?
    public static void main(String[]/* \u005B\u005D */ args) {
        System.out.println("Test Unicode");

        String u = "\u0022+\u0022";
        System.out.println(u.length());

        // look inside c:\users

        // char mu = '\udb35\udd46'; //Too many characters in character literal

    }
}