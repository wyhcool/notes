import java.util.Arrays;

/**
 * 使用 Comparable 接口排序
 *
 * @version 1.0.0 2020-01-18
 * @author bruce
 */
public class EmployeeSortTest {

    public static void main(String[] args) {

        Employee[] staff = new Employee[3];

        staff[0] = new Employee("Herry", 35000, 2019, 1, 1);
        staff[1] = new Employee("Carl", 75000, 2011, 1, 1);
        staff[2] = new Employee("Tony", 38000, 2018, 1, 1);

        Arrays.sort(staff);

        for (Employee e : staff) {
            System.out.println("name=" + e.getName() + ",salary=" + e.getSalary());
        }
    }
}
