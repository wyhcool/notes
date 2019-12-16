import java.util.*;

/**
 * @version 1.0.1 2019-12-13
 * @author bruce
 */
public class StringJoinTest {

    public static void main(String[] args) {
        //字符串数组
        String[] strArray = {"Hello", "Java", "String"};

        //字符串队列
        List<String> strList = new ArrayList<String>();
        //添加元素
        strList.add("Hello");
        strList.add("Java");
        strList.add("String");

        //第2个参数是字符串数组
        String a = String.join("!", strArray);
        //第2个参数是字符串队列
        String b = String.join("!", strList);
        //第1个参数之后是多个字符串
        String c = String.join("!", "Hello", "Java", "String");

        System.out.println(a); //Hello!Java!String
        System.out.println(b); //Hello!Java!String
        System.out.println(c); //Hello!Java!String
    }
}
