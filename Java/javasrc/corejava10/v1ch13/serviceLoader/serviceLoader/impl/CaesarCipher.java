package serviceLoader.impl;

import serviceLoader.Cipher;

/**
 * caesar cipher
 * 凯撒加密法，或称恺撒加密、恺撒变换、变换加密，
 * 是一种最简单且最广为人知的加密技术。
 * 它是一种替换加密的技术，
 * 明文中的所有字母都在字母表上向后（或向前）按照一个固定数目进行偏移后被替换成密文。
 *
 * @version 1.0.0 2020-02-23
 * @author bruce
 */
public class CaesarCipher implements Cipher {

    public byte[] encrypt(byte[] source, byte[] key) {
        byte[] result = new byte[source.length];
        for (int i = 0; i < source.length; i++) {
            result[i] = (byte)(source[i] + key[0]);
        }
        return result;
    }

    public byte[] decrypt(byte[] source, byte[] key) {
        return encrypt(source, new byte[] { (byte) -key[0]});
    }

    public int strength() {
        return 1;
    }
}
