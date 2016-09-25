package test;

import de.datainspector.DataInspector;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("integrationTest")
@DataInspector
public class IntegrationApplConfiguration {
}
