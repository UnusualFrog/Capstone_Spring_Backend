package org.example.capstone.config;

import org.jasypt.util.password.StrongPasswordEncryptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for defining the encryption bean used for password hashing.
 * This configuration enables secure password storage using Jasypt's {@link StrongPasswordEncryptor},
 * which applies salted SHA-256 encryption.
 */
@Configuration
public class EncryptionConfig {

    /**
     * Bean definition for the StrongPasswordEncryptor.
     * This bean can be injected wherever password encryption or verification is needed.
     * @return A StrongPasswordEncryptor instance.
     */
    @Bean
    public StrongPasswordEncryptor strongPasswordEncryptor() {
        return new StrongPasswordEncryptor();
    }
}
