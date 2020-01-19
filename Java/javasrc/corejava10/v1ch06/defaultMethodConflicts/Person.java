/**
 * Person 接口
 *
 * @version 1.0.0 2020-01-19
 * @author bruce
 */
public interface Person {

    default String getName() {
        return "Person:" + getClass().getName() + "_" + hashCode();
    }

}
