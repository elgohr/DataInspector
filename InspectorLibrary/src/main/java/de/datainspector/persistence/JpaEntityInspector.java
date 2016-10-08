package de.datainspector.persistence;

import de.datainspector.AbstractDataInspector;
import de.datainspector.businessobject.DataObject;
import org.reflections.Reflections;
import org.springframework.stereotype.Component;

import javax.persistence.Entity;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Component
public class JpaEntityInspector extends AbstractDataInspector {

    private final static String inspectorName = "persistence";

    private Set<Class<?>> entityClasses;
    private final List blockedFields;

    public JpaEntityInspector() {
        entityClasses = new Reflections("")
                .getTypesAnnotatedWith(Entity.class);
        blockedFields = Arrays.asList("$staticClassInfo", "__$stMC",
                "metaClass", "this$0", "$staticClassInfo$", "$callSiteArray",
                "$jacocoData");
    }

    @Override
    public DataObject getDataObjects() {
        DataObject inspectorObject = new DataObject(inspectorName);
        entityClasses
                .parallelStream()
                .forEach((entityClass) -> {
                    DataObject entityDataObject = new DataObject(entityClass.getName());
                    Arrays.stream(entityClass
                            .getDeclaredFields())
                            .forEach(declaredField -> {
                                if (!blockedFields.contains(declaredField.getName())) {
                                    DataObject fieldDataObject = new DataObject(declaredField.getName());
                                    entityDataObject.addChild(fieldDataObject);
                                }
                            });
                    inspectorObject.addChild(entityDataObject);
                });
        return inspectorObject;
    }

}
