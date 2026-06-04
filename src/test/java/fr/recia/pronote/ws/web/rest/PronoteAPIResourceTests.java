package fr.recia.pronote.ws.web.rest;

import fr.recia.pronote.ws.AbstractPronoteWsApplicationTests;
import fr.recia.pronote.ws.config.TestKeysConfig;
import fr.recia.pronote.ws.service.util.XmlValidatorImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.util.Assert;


import java.io.File;
import java.util.Objects;

import static com.jayway.jsonpath.internal.Utils.isTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@SpringBootTest(properties = "spring.profiles.active=test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Import(TestKeysConfig.class)
public class PronoteAPIResourceTests extends AbstractPronoteWsApplicationTests {

    @Test
    void xmlShouldMatchXsd() throws Exception {


        File file = new File(Objects.requireNonNull(PronoteAPIResourceTests.class.getResource("/xsd/ConteneurImportChiffre.xsd")).toURI());
        XmlValidatorImpl validator = new XmlValidatorImpl(file);

        Assert.notNull(uaiTest, "UAI is null, cannot run test with current configuration");

        MvcResult result = mockMvc.perform(get(String.format("/api/export/%s",uaiTest)))
                .andExpect(status().isOk())
                .andReturn();
        String xml = result.getResponse().getContentAsString();

        boolean validated = validator.validate(xml);
        isTrue(validated, "XML file validation against XSD should return true");

    }
}
