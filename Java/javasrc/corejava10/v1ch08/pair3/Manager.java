/**
 * 经理类
 * @version 1.0.1 2020-01-04
 * @author bruce
 */
public class Manager extends Employee {

    private double bonus;

    public Manager(String name, double salary, int year, int month, int day) {
        super(name, salary, year, month, day);
        bonus = 0;
    }

    public void setBonus(double bonus) {
        this.bonus = bonus;
    }

    public double getBonus() {
        return bonus;
    }

    public double getSalary(){
        double salary = super.getSalary();
        return salary + bonus;
    }
}
