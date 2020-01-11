import java.time.LocalDate;
import java.util.Objects;

/**
 * 雇员类
 *  增加 equals 方法
 * @version 1.0.3 2020-01-08
 * @author bruce
 */
public class Employee {

    private String name;
    private double salary;
    private LocalDate hireDay;

    public Employee(String name, double salary, int year, int month, int day) {
        this.name = name;
        this.salary = salary;
        this.hireDay = LocalDate.of(year, month, day);
    }

    public double getSalary() {
        return salary;
    }

    public LocalDate getHireDay() {
        return hireDay;
    }

    public void raiseSalary(int byPercent) {
        double raise = salary * byPercent / 100;
        salary += raise;
    }

    public String getDescription() {
        return "Employee name=" + name + ";salary=" + getSalary() + ";hireDay=" + hireDay.toString();
    }

    @Override public boolean equals(Object otherObject) {

        // a quick test to see if the objects are identical 完全相同的
        if (this == otherObject) return true;

        // must return false if explicit parameter is null
        if (otherObject == null) return false;

        // if the classes don't match, they can't be equal
        // getClass 方法返回一个对象所属的类
        if (getClass() != otherObject.getClass()) {
            return false;
        }

        // now we know otherObject is a non-null Employee
        Employee other = (Employee) otherObject;

        // test whether the fields have identical values
        return name.equals(name)
                && salary == other.salary
                && Objects.equals(hireDay, other.hireDay); // hireDay.equals(other.hireDay);
    }

    @Override public int hashCode(){
        return Objects.hash(name, salary, hireDay);
    };

    @Override public String toString() {
        return getClass().getName()
            + "[name=" + name
            + ",salary=" + salary
            + ",hireDay=" + hireDay
            + "]";
    }
}
