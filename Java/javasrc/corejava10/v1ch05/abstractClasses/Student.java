/**
 * 学生
 * @version 1.0.1 2020-01-07
 * @author bruce
 */
public class Student extends Person {

    private String id;

    public Student(String name, String id) {
        super(name);
        this.id = id;
    }

    public String getDescription() {
        return "Student name=" + getName() + ";id=" + id;
    }
}
