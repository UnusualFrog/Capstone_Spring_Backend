package org.example.capstone.config;

import com.thoughtworks.xstream.XStream;
import org.example.capstone.pojos.RiskFactors;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.*;

/**
 * Configuration class responsible for loading or initializing the {@link RiskFactors} bean.
 * <p>
 * This class attempts to read risk factor settings from an XML config file on startup. If the file doesn't exist,
 * it creates one with default values and saves it to disk using XStream serialization.
 */
@Configuration
public class RiskFactorConfig {

    /**
     * Initializes and returns the {@link RiskFactors} bean.
     * <ul>
     *     <li>If the config file exists: it loads and parses it using XStream.</li>
     *     <li>If the config file does not exist: it creates a new one with default values and persists it.</li>
     * </ul>
     *
     * @return A {@link RiskFactors} instance populated from XML or default values.
     */
    @Bean
    public RiskFactors riskFactor() {
        RiskFactors riskFactors = new RiskFactors();
        try {
            File myObj = new File(".\\src\\main\\java\\org\\example\\capstone\\config\\risk_factor_config.xml");
            XStream xstream = new XStream();
            xstream.allowTypesByWildcard(new String[]{"org.example.capstone.**"});
            if (myObj.createNewFile()) {
                try {
                    FileWriter myWriter = new FileWriter(".\\src\\main\\java\\org\\example\\capstone\\config\\risk_factor_config.xml");
                    riskFactors.loadDefaultValues();
                    String dataXml = xstream.toXML(riskFactors);
                    myWriter.write(dataXml);
                    myWriter.close();
                    System.out.println("Success: Config File '" + myObj.getName() + "' created.");
                } catch (IOException e) {
                    System.out.println("Failure: Config File '" + myObj.getName() + "' could not be created.");
                    e.printStackTrace();
                }
            } else {
                String filePath = ".\\src\\main\\java\\org\\example\\capstone\\config\\risk_factor_config.xml"; // The file path
                StringBuilder contentBuilder = new StringBuilder();
                try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        contentBuilder.append(line).append(System.lineSeparator());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String fileContent = contentBuilder.toString();
                riskFactors = (RiskFactors) xstream.fromXML(fileContent);
                System.out.println("Success: Config File '" + myObj.getName() + "' loaded.");
            }
        } catch (IOException e) {
            System.out.println("Error: Config File could not be created or loaded.");
            e.printStackTrace();
        }
        return riskFactors;
    }
}
