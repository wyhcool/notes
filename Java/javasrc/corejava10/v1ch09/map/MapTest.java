import java.util.*;

/**
 * the use of a map with key type String and value type Employee
 *
 * @version 1.0.0 2020-02-14
 * @author bruce
 */
public class MapTest {

    public static void main(String[] args) {

        Map<String, Employee> staff = new HashMap<>();
        staff.put("144-25-5464", new Employee("Amy Lee", 10000, 1994, 11, 12));
        staff.put("567-25-5464", new Employee("Harry Hacker", 10000, 1994, 11, 12));
        staff.put("157-25-5464", new Employee("Gary Cooper", 10000, 1994, 11, 12));
        staff.put("456-25-5464", new Employee("Francesca Cruz", 10000, 1994, 11, 12));

        System.out.println(staff);

        staff.remove("567-25-5464");

        staff.put("456-21-5464", new Employee("Francesca Miller", 10000, 1994, 11, 12));

        System.out.println(staff.get("157-25-5464"));

        staff.forEach((k, v) -> {
            System.out.println("key=" + k + ", value=" + v);
        });
    }
}
