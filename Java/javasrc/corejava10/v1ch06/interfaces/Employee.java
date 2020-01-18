import java.time.LocalDate;

/**
 * 雇员类
 *  增加排序方法
 * @version 1.0.1 2020-01-18
 * @author bruce
 */
public class Employee implements Comparable<Employee> {

    private String name;
    private double salary;
    private LocalDate hireDay;

    public Employee(String name, double salary, int year, int month, int day) {
        this.name = name;
        this.salary = salary;
        this.hireDay = LocalDate.of(year, month, day);
    }

    public String getName() {
        return name;
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

    public int compareTo(Employee other) {
        return Double.compare(salary, other.salary);
    }
}
