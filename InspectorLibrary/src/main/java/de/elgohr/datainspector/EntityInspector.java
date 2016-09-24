package de.elgohr.datainspector;

import org.reflections.Reflections;
import org.springframework.stereotype.Component;

import javax.persistence.Entity;
import java.util.*;

@Component
public class EntityInspector {

    private Set<Class<?>> entityClasses;
    private List blockedFields;

    public EntityInspector() {
        entityClasses = new Reflections("")
                .getTypesAnnotatedWith(Entity.class);
        blockedFields = Arrays.asList("$staticClassInfo", "__$stMC",
                "metaClass", "this$0", "$staticClassInfo$", "$callSiteArray");
    }

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
