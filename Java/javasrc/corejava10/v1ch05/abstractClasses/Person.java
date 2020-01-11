/**
 * 抽象类 - 人
 * @version 1.0.1 2020-01-07
 * @author bruce
 */
public abstract class Person {

    private String name;

    public Person(String name) {
        this.name = name;
    }

    public abstract String getDescription();

    public String getName() {
        return name;
    }
}
