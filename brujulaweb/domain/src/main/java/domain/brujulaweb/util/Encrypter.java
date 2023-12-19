package domain.brujulaweb.util;

public interface Encrypter {
    String encrypt(String password);

    boolean verify(String password, String hash);
}
