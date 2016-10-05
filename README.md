[![Build Status](https://travis-ci.org/elgohr/DataInspector.svg?branch=master)](https://travis-ci.org/elgohr/DataInspector)
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
