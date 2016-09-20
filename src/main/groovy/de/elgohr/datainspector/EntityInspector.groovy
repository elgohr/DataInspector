package de.elgohr.datainspector

import org.reflections.Reflections
import org.springframework.stereotype.Component

import javax.persistence.Entity

@Component
class EntityInspector {

    def classes = new Reflections("").getTypesAnnotatedWith(Entity.class)
    def blockedFields = Arrays.asList('$staticClassInfo', '__$stMC',
            'metaClass', 'this$0', '$staticClassInfo$', '$callSiteArray')

    HashMap getAttributesPerClass() {
        def attributesPerClass = new HashMap()
        classes.each {
            def fields = new LinkedList<>()
            it.getDeclaredFields().each {
                if (!blockedFields.contains(it.getName())) {
                    fields.add(it.getName())
                }
            }
            attributesPerClass.put(it.getName(), fields)
        }
        return attributesPerClass
    }
}
