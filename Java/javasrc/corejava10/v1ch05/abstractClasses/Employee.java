import java.time.LocalDate;

/**
 * 雇员类
 * @version 1.0.2 2020-01-07
 * @author bruce
 */
public class Employee extends Person {

    private double salary;
    private LocalDate hireDay;

    public Employee(String name, double salary, int year, int month, int day) {
        super(name);
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
        return "Employee name=" + getName() + ";salary=" + getSalary() + ";hireDay=" + hireDay.toString();
    }
}
