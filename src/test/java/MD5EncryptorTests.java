import com.gosecuri.security.MD5Encryptor;
import org.junit.jupiter.api.Test;

import java.security.NoSuchAlgorithmException;

import static org.junit.jupiter.api.Assertions.*;

public class MD5EncryptorTests {

    @Test
    public void encrypt_password_0000() throws NoSuchAlgorithmException {
        MD5Encryptor md5 = new MD5Encryptor();
        String password = "0000";
        String expected = "4a7d1ed414474e4033ac29ccb8653d9b";
        String actual = md5.encrypt(password);

        assertEquals(expected, actual);
    }

    @Test
    public void encrypt_password_azerty() throws NoSuchAlgorithmException {
        MD5Encryptor md5 = new MD5Encryptor();
        String password = "azerty";
        String expected = "ab4f63f9ac65152575886860dde480a1";
        String actual = md5.encrypt(password);

        assertEquals(expected, actual);
    }

    @Test
    public void encrypt_password_a5gzghe86hze5heH54ez() throws NoSuchAlgorithmException {
        MD5Encryptor md5 = new MD5Encryptor();
        String password = "a5gzghe86hze5heH54ez";
        String expected = "aa4403916b742516bae395867a009241";
        String actual = md5.encrypt(password);

        assertEquals(expected, actual);
    }

}
