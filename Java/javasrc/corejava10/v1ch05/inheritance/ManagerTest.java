/**
 * 测试
 * @version 1.0.1 2020-01-04
 * @author bruce
 */
public class ManagerTest {

    public static void main(String[] args) {

        Manager boss = new Manager("Yakui Wang", 10_000, 2019, 4, 28);
        boss.setBonus(2000);

        Employee[] staff = new Employee[3];
        staff[0] = boss;
        staff[1] = new Employee("Harry Potter", 20_000, 2018, 5,1);
        staff[2] = new Employee("Smithy", 30_000,2019,3,2);

        //staff[0].setBonus(2000);

        for (Employee e : staff) {
            System.out.println("name=" + e.getName() + ";salary=" + e.getSalary());
        }

        Manager[] managers = new Manager[10];
        Employee[] staff1 = managers; //OK
        //staff1[0] = new Employee("Bob", 30_000,2019,3,2);

        //Manager boss2 = (Manager) staff[1];

        System.out.println(null instanceof Employee);

        System.out.println(managers[0]);
    }
}
