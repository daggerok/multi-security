package daggerok.multi.utils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = UtilsApplication.class)
public class UtilsApplicationTest {
    @Autowired
    ApplicationContext applicationContext;

    @Test
    public void testApplicationContext() throws Exception {
        assertNotNull("null application context", applicationContext);
    }

    @Test
    public void testApplicationBeans() throws Exception {
        assertTrue("passwordGenerator bean wasn't found", applicationContext.containsBean("passwordGenerator"));

        assertTrue("utilsApplication bean wasn't found", applicationContext.containsBean("utilsApplication"));
    }
}