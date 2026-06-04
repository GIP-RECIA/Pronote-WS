package fr.recia.pronote.ws;

import fr.recia.pronote.ws.config.TestKeysConfig;
import fr.recia.pronote.ws.web.rest.PronoteAPIResourceTests;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

@Slf4j
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Import(TestKeysConfig.class)
@SpringBootTest
public class AbstractPronoteWsApplicationTests {

    @Value("${test.use-localhost-ldap}")
    boolean useLocalhostLdap;
    // when running test locally, use a ldap running in a docker exposed on localhost

    @Value("${test.uai}")
    protected String uaiTest;

    @Autowired
    private WebApplicationContext webApplicationContext;
    protected MockMvc mockMvc;

    @BeforeAll
    protected void init() throws Exception {

        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        log.info("use local ldap {}", useLocalhostLdap);

        if(useLocalhostLdap){
            try (Socket socket = new Socket()) {
                socket.connect(new InetSocketAddress("localhost", 389), 1000);
            } catch (IOException e) {
                LoggerFactory.getLogger(PronoteAPIResourceTests.class)
                        .error("Is the OpenLDAP Docker running ? (localhost:389 unreachable)");

                Assumptions.assumeTrue(false,
                        "Skipping tests: OpenLDAP not available on localhost:389");
            }
        }


    }
}
