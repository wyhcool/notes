import java.io.Console;
import java.util.*;

/**
 * @version 1.0.1 2019-12-19
 * @author bruce
 */
public class InputTest {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        //get a line
        System.out.print("What's your name?");
        String name = in.nextLine();

        //get a word / int / double
        System.out.print("How old are you?");
        int age = in.nextInt();

        System.out.printf("Hello, %s. Next year, you'll be %d.", name, age + 1);


//        String space1 = in.next();
//        String space2 = in.next();
//
//        System.out.println(space1.length());
//        System.out.println(space2.length());

        //Console 输入： (注：该种方法不能在 idea 中使用，必须使用控制台)
        Console cons = System.console();
        String username = cons.readLine("User name: ");
        char[] passwd = cons.readPassword("Password: ");

        System.out.println(username);
        System.out.println(passwd);

    }

}