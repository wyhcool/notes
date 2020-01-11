import java.util.ArrayList;

/**
 * ArrayList
 *
 * @version 1.0.0 2020-01-11
 * @author bruce
 */
public class ArrayListTest {

    public static void main(String[] args) {

        ArrayList<Employee> staff = new ArrayList<>();

        staff.add(new Employee("Yakui", 10500, 2019, 4, 28));
        staff.add(new Employee("bruce", 9000, 2019, 7, 28));

        for (Employee e : staff) {
            System.out.println(e);
        }
    }
}
