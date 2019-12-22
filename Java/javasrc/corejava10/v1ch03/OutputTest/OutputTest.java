/**
 * @version 1.0.1 2019-12-20
 * @author bruce
 */
public class OutputTest {

    public static void main(String[] args) {
        double x = 10000.0 / 3.0;
        System.out.print(x); //3333.3333333333335

        //用 8 个字符的宽度和小数点后 2 个字符的精度
        System.out.printf("%8.2f", x);
        System.out.println();

        String name = "bruce";
        int age = 25;
        System.out.printf("Hello, %s. Next year, you'll be %d", name, age + 1);
        System.out.println();

        //逗号增加分组的分隔符
        System.out.println("加号");
        System.out.printf("%+.2f", 10000.0 / 3.0);
        System.out.println();
        System.out.printf("%+.2f", -10000.0 / 3.0);
        System.out.println();
        System.out.println("空格");
        System.out.printf("% .2f", 10000.0 / 3.0);
        System.out.println();
        System.out.printf("% .2f", -10000.0 / 3.0);
        System.out.println();
        System.out.println("补0");
        System.out.printf("%08.2f", 10000.0 / 3.0);
        System.out.println();
        System.out.printf("%08.2f", -10000.0 / 3.0);
        System.out.println();
        System.out.println("左对齐");
        System.out.printf("%-8.2f", 10000.0 / 3.0);
        System.out.printf("%8.1f", -10000.0 / 3.0);
        System.out.println();
        System.out.println("括号");
        System.out.printf("%(8.2f", -1000000000.0 / 3.0);

    }
}

