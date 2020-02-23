package serviceLoader;

/**
 * cipher
 *
 * @version 1.0.0 2020-02-23
 * @author bruce
 */
public interface Cipher {
    byte[] encrypt(byte[] source, byte[] key);
    byte[] decrypt(byte[] source, byte[] key);
    int strength();
}
