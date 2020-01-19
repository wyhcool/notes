/**
 * 多接口实现
 *
 * @version 1.0.0 2020-01-19
 * @author bruce
 */
public class Student implements Named, Person {

    private String name;

    public Student(String name) {
        this.name = name;
    }

//    public String getName() {
//        return Named.super.getName();
//    }

    public static void main(String[] args) {

        Student student = new Student("Yakui");
        System.out.println(student.getName());
    }
}
