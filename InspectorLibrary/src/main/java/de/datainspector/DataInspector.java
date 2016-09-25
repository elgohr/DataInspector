package de.datainspector;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"de.datainspector"})
public @interface DataInspector {
}
