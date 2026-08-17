package fr.recia.pronote.ws.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PublicKey;


@TestConfiguration
public class TestKeysConfig {

    @Bean
    @Primary
    PublicKey publicKeyTest() throws Exception {
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(2048);

        keyPair = generator.generateKeyPair();

        return keyPair.getPublic();
    }

    static KeyPair keyPair;

}