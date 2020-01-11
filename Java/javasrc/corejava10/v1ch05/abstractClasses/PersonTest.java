/**
 * 测试类
 * @version 1.0.1 2020-01-07
 * @author bruce
 */
public class PersonTest {

    public static void main(String[] args) {

        Person[] people = new Person[2];

        //fill Person array width Student and Employee
        people[0] = new Student("Yakui", "13211105");
        people[1] = new Employee("Bruce", 10500, 2019, 4, 28);

        for (Person p : people) {
            System.out.println(p.getDescription());
        }

    }
}
