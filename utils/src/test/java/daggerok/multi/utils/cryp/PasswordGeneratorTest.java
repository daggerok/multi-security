package daggerok.multi.utils.cryp;

import daggerok.multi.utils.UtilsApplication;
import daggerok.multi.utils.cryp.PasswordGenerator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = UtilsApplication.class)
public class PasswordGeneratorTest {
    @Autowired
    PasswordGenerator passwordGenerator;

    @Test
    public void testEncoder() throws Exception {
        String password = "max";
        String encodedPassword1 = passwordGenerator.encode(password);
        String encodedPassword2 = passwordGenerator.encode(password);

        assertNotEquals("passwords must not be same", encodedPassword1, encodedPassword2);    }

    @Test(expected = NullPointerException.class)
    public void testEncoderNegative() throws Exception {
        try {
            passwordGenerator.encode(null);
            fail("should throw NullPointerException");
        } catch (NullPointerException e) {
            assertEquals("wrong message", "password", e.getMessage());
            throw e;
        }
    }
}