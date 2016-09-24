package test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"de.elgohr.datainspector"})
public class TestAppl {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(TestAppl.class, args);
    }
}
