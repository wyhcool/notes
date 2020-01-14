import java.lang.reflect.Constructor;

/**
 * newInstance
 *
 * @version 1.0.0 2020-01-14
 * @author bruce
 */
public class NewInstanceTest {

    public static void main(String[] args) {
        NewInstanceTest test = new NewInstanceTest();

        System.out.println("通过Class.NewInstance()调用私有构造函数:");
        test.newInstanceByClassNewInstance();

        System.out.println("通过Constructor.NewInstance()调用私有构造函数:");
        test.newInstanceByConstructorNewInstance();

    }

    private void newInstanceByClassNewInstance() {
        try {
            String className = "A";
            Class aClass = Class.forName(className);
            A a = (A)aClass.newInstance();
        } catch (Exception e) {
            System.out.println("通过 Class.newInstance 调用私有构造函数[失败]");
        }
    }

    private void newInstanceByConstructorNewInstance() {
        try {
            String className = "A";
            Class aClass = Class.forName(className);
            /* 调用无参私有构造函数 */
            Constructor c0 = aClass.getDeclaredConstructor();
            c0.setAccessible(true);
            A a0 = (A)c0.newInstance();
            /* 调用有参私有构造函数 */
            Constructor c1 = aClass.getDeclaredConstructor(new Class[] {int.class, int.class});
            c1.setAccessible(true);
            A a1 = (A)c1.newInstance(new Object[]{5, 6});
        } catch (Exception e) {
            System.out.println("通过 Constructor.newInstance 调用私有构造函数[失败]");
        }
    }
}

class A {
    private A() {
        System.out.println("A's constructor(no-argument) is called.");
    }

    private A(int a, int b) {
        System.out.println("A's constructor(arguments) is called.");
    }

}