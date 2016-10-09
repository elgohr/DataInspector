[![Build Status](https://travis-ci.org/elgohr/DataInspector.svg?branch=master)](https://travis-ci.org/elgohr/DataInspector)
[![codecov.io](https://codecov.io/gh/elgohr/DataInspector/coverage.svg?branch=master)](https://codecov.io/gh/elgohr/DataInspector?branch=master)
# DataInspector
This library publishes the metadata of your Spring Boot application entities to the /data endpoint. The output format fits for typical [d3 visualizations](https://d3js.org/).

```json
{
    "name": "my-crm",
    "children": [{
        "name": "persistence",
        "children": [{
            "name": "Customer",
            "children": [{
                "name": "name"
            }, {
                "name": "age"
            }]
        }]
    }]
}
```

### Usage  
Just add the library as a dependency to your buildfile (publishing to maven central as soon as possible) and add the @DataInspector-Annotation to your configuration or application. Start using javax.persistence.entities :-)

```java
@SpringBootApplication
@DataInspector
public class YourApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(YourApplication.class, args);
    }
}
```
