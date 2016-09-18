package de.elgohr.datainspector

import org.reflections.Reflections
import org.springframework.stereotype.Component

import javax.persistence.Entity
import java.lang.reflect.Field

@Component
class EntityInspector {

    def classes = new Reflections("").getTypesAnnotatedWith(Entity.class)
    def blockedFields = Arrays.asList('$staticClassInfo', '__$stMC',
            'metaClass', 'this$0', '$staticClassInfo$', '$callSiteArray')

    HashMap getAttributesPerClass() {
        def attributesPerClass = new HashMap()
        for (Class<?> classEntity : classes) {
            def fields = new LinkedList<>()
            for (Field field : classEntity.getDeclaredFields()) {
                if (!blockedFields.contains(field.getName())) {
                    fields.add(field.getName())
                }
            }
            attributesPerClass.put(classEntity.getName(), fields)
        }
        return attributesPerClass
    }
}
