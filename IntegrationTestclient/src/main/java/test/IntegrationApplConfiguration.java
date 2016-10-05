package test;

import de.datainspector.DataInspector;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile({"integration", "noApplicationName"})
@DataInspector
public class IntegrationApplConfiguration {
}
