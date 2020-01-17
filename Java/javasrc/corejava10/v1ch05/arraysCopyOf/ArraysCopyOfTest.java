import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * 扩展任意类型的数组
 *
 * @version 1.0.0 2020-01-17
 * @author bruce
 */
public class ArraysCopyOfTest {

    public static void main(String[] args) {

        int[] a = {1, 2, 3, 4, 5};
        System.out.println(Arrays.toString(a));
        a = (int[]) arraysCopyOf(a, 10);
        System.out.println(Arrays.toString(a));

    }

    public static Object arraysCopyOf(Object a, int newLength) {
        Class cl = a.getClass();
        if (!cl.isArray()) return null;
        Class componentType = cl.getComponentType();
        int length = Array.getLength(a);
        Object newArray = Array.newInstance(componentType, newLength);
        System.arraycopy(a, 0, newArray, 0, Math.min(length, newLength));
        return newArray;
    }
}
