package de.datainspector;

import de.datainspector.persistence.JpaEntityInspector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.Map;

@RestController
public class DataInspectorController {

    private JpaEntityInspector jpaEntityInspector;

    @Autowired
    DataInspectorController(JpaEntityInspector jpaEntityInspector) {
        this.jpaEntityInspector = jpaEntityInspector;
    }

    @RequestMapping(
            value = "/data",
            method = RequestMethod.GET,
            produces= MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Map<String, LinkedList<String>> publishInspectedData() {
        return jpaEntityInspector.getAttributesPerClass();
    }
}
