package de.datainspector.persistence;

import de.datainspector.AbstractDataInspector;
import org.reflections.Reflections;
import org.springframework.stereotype.Component;

import javax.persistence.Entity;
import java.util.*;

@Component
public class JpaEntityInspector extends AbstractDataInspector {

    private Set<Class<?>> entityClasses;
    private List blockedFields;

    public JpaEntityInspector() {
        entityClasses = new Reflections("")
                .getTypesAnnotatedWith(Entity.class);
        blockedFields = Arrays.asList("$staticClassInfo", "__$stMC",
                "metaClass", "this$0", "$staticClassInfo$", "$callSiteArray");
    }

    @Override
    public String getInspectorName() {
        return "JpaEntityInspector";
    }

    @Override
    public HashMap getAttributesPerClass() {
        HashMap attributesPerClass = new HashMap<String, LinkedList<String>>();
        entityClasses
                .parallelStream()
                .forEach((entityClass) -> {
                    LinkedList fields = new LinkedList<String>();
                    Arrays.stream(entityClass
                            .getDeclaredFields())
                            .forEach(declaredField -> {
                                if (!blockedFields.contains(declaredField.getName())) {
                                    fields.add(declaredField.getName());
                                }
                            });
                    attributesPerClass.put(entityClass.getName(), fields);
                });
        return attributesPerClass;
    }

}
