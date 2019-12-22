import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;

/**
 * @version 1.0.1 2019-12-21
 * @author bruce
 */
public class FileTest {

    public static void main(String[] args) throws IOException {

        String dir = System.getProperty("user.dir");
        System.out.println(dir);

        Scanner fileIn = new Scanner(Paths.get("src/myfile.txt"), "UTF-8");

        System.out.println(fileIn.nextLine());
    }
}
