package org.example.capstone.config;

import com.thoughtworks.xstream.XStream;
import org.example.capstone.pojos.RiskFactors;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.*;

@Configuration
public class RiskFactorConfig {
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
                System.out.println(riskFactors.getAutoBasePremium());
            }
        } catch (IOException e) {
            System.out.println("Error: Config File could not be created or loaded.");
            e.printStackTrace();
        }
        return riskFactors;
    }
}
