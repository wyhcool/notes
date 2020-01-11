import java.util.Objects;

/**
 * 经理类
 *  增加 equals 方法
 *  增加 hashCode 方法
 *  增加 toString 方法
 *
 * @version 1.0.2 2020-01-09
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

    public double getSalary(){
        double salary = super.getSalary();
        return salary + bonus;
    }

    @Override public boolean equals(Object otherObject) {
        if (!super.equals(otherObject)) return false;

        // super.equals() checked that this and otherObject belong to the same class
        Manager other = (Manager) otherObject;
        return bonus == other.bonus;
    }

    @Override public int hashCode(){
        return super.hashCode() + Double.hashCode(bonus);
    };

    @Override public String toString() {
        return super.toString() +  "[bonus=" + bonus + "]";
    }

}
