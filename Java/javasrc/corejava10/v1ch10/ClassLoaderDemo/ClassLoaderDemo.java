import java.net.URL;

/**
 * java.lang.ClassLoader.getSystemResource() 方法的使用
 *
 * @version 1.0.0 2020-02-28 00:21
 * @author bruce
 */
public class ClassLoaderDemo {

    public static void main(String[] args) throws ClassNotFoundException {


        /**
         *
         * URL url = ClassLoader.getSystemResource("tessdata");
         * vs.
         * URL url = ChangeUtil.class.getClassLoader().getResource("tessdata");
         * 区别 ?
         */
        Class cls = Class.forName("ClassLoaderDemo");

        // returns the ClassLoader object associated with this Class
        ClassLoader cLoader = cls.getClassLoader();

        System.out.println(cLoader.getClass());

        // find resources
        URL url = cLoader.getSystemResource("a.txt");
        System.out.println("Value = " + url);

        // finds resource
        url = cLoader.getSystemResource("newfolder/a.txt");
        System.out.println("Value = " + url);


    }

}
