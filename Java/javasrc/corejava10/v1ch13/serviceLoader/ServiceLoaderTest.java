import serviceLoader.Cipher;

import java.util.ServiceLoader;

/**
 * use reflection to print all features of a class
 *
 * @version 1.0.0 2020-02-23
 * @author bruce
 */
public class ServiceLoaderTest {

    public static void main(String[] args) {

        ServiceLoader<Cipher> loader = ServiceLoader.load(Cipher.class);

        for (Cipher service : loader) {
            System.out.println(service.getClass());
        }
    }

}