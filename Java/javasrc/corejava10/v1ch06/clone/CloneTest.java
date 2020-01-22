/**
 * 克隆
 *
 * @version 1.0.0 2020-01-22
 * @author bruce
 */
public class CloneTest {

    public static void main(String[] args) {

       try {
           Employee original = new Employee("Yakui", 10500);
           original.setHireDay(2019, 4, 28);
           Employee copy = original.clone();
           copy.raiseSalary(10);
           copy.setHireDay(2019, 2,2);
           System.out.println("original=" + original);
           System.out.println("copy    =" + copy);
       } catch (CloneNotSupportedException ex) {
           ex.printStackTrace();
       }
    }
}
