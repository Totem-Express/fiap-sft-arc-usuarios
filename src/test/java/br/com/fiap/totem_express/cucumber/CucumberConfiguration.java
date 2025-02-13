package br.com.fiap.totem_express.cucumber;

import br.com.fiap.totem_express.DynamoTestConfiguration;
import br.com.fiap.totem_express.TestContainerConfiguration;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@CucumberContextConfiguration
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
@Import({ TestContainerConfiguration.class, DynamoTestConfiguration.class})
public class CucumberConfiguration {
}