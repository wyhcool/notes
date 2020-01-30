import java.util.Scanner;

/**
 * 打印递归阶乘函数的堆栈情况
 *
 * @version 1.0.0 2020-01-30
 * @author bruce
 */
public class StackTraceTest {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.print("Enter n: ");
        int n = in.nextInt();
        factorial(n);
    }

    public static int factorial(int n) {
        System.out.println("factorial(" + n + "):");
        Throwable t = new Throwable();
        StackTraceElement[] frames = t.getStackTrace();
        for (StackTraceElement f : frames) {
            System.out.println(f);
        }
        int r;
        if (n <= 1) r = 1;
        else r = n * factorial(n-1);
        System.out.println("return " + r);
        return r;
    }
}
