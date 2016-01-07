package daggerok.multi.data;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@WebIntegrationTest({"server.port:0", "management.port:0"})
@ContextConfiguration(classes = DataCfg.class)
public class DataCfgTest {
    @Autowired
    ApplicationContext applicationContext;
    @Value("${local.server.port:0}")
    private int port;

    @Test
    public void testApplicationContext() {
        assertNotNull("null application context", applicationContext);
    }

    @Test
    public void testBeans() {
        assertTrue("dataCfg bean wasn't found", applicationContext.containsBean("dataCfg"));

        assertTrue("h2servletRegistration bean wasn't found", applicationContext.containsBean("h2servletRegistration"));

        assertTrue("userRepository bean wasn't found", applicationContext.containsBean("userRepository"));
    }
}