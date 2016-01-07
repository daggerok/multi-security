package daggerok.multi.web;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.Charset;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

//import org.springframework.test.context.web.WebAppConfiguration;
//@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@WebIntegrationTest({"server.port:0", "management.port:0"})
@SpringApplicationConfiguration(classes = WebApplication.class)
public class WebApplicationTest {
    @Value("${local.server.port:0}")
    private int port;

    protected String url() {
        return "http://localhost:" + port;
    }

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ApplicationContext applicationContext;

    protected MockMvc mockMvc;

    protected MediaType jsonContentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    @Before
    public void setUp() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testApplicationContext() {
        assertNotNull("null application context", applicationContext);
        assertNotNull("null web application context", webApplicationContext);
        assertEquals(applicationContext, webApplicationContext);
    }

    @Test
    public void testApplicationBeans() {
        assertTrue("webCfg bean wasn't found", applicationContext.containsBean("webCfg"));
        assertTrue("initializer bean wasn't found", applicationContext.containsBean("initializer"));

        assertTrue("userDetailsImpl bean wasn't found", applicationContext.containsBean("userDetailsImpl"));
        assertTrue("userDetailsServiceImpl bean wasn't found", applicationContext.containsBean("userDetailsServiceImpl"));

        assertTrue("webSecurityCfg bean wasn't found", applicationContext.containsBean("webSecurityCfg"));
        assertTrue("csrfTokenGeneratorFilter bean wasn't found", applicationContext.containsBean("csrfTokenGeneratorFilter"));

        assertTrue("indexController bean wasn't found", applicationContext.containsBean("indexController"));

        assertTrue("webApplication bean wasn't found", applicationContext.containsBean("webApplication"));
    }
}