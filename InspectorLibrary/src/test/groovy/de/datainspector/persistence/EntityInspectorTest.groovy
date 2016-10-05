package de.datainspector.persistence

import de.datainspector.testclasses.TestEntity
import spock.lang.Specification

class EntityInspectorTest extends Specification {

    JpaEntityInspector inspector

    def setup() {
        inspector = new JpaEntityInspector()
    }

    def "should inspect only classes, which are annotated with @Entity"() {
        when:
        def classes = inspector.entityClasses
        then:
        classes.size() == 1
        classes.contains(TestEntity)
    }

    def "should own inspector name on top"() {
        when:
        def dataObjects = inspector.getDataObjects()
        then:
        dataObjects.getName() == "persistence"
    }

    def "should return inspected classes"() {
        when:
        def dataObjects = inspector.getDataObjects()
        then:
        dataObjects.getChildren()[0].getName() == TestEntity.class.getName()
    }

    def "should return the attributes of the classes in relation"() {
        when:
        def dataObjects = inspector.getDataObjects()
        then:
        dataObjects.getChildren()[0].getName() == TestEntity.class.getName()
        dataObjects.getChildren()[0].getChildren().size() == 2
        dataObjects.getChildren()[0].getChildren()[0].getName() == "name"
        dataObjects.getChildren()[0].getChildren()[1].getName() == "age"
    }

    def "should not return the standard fields of the classes"() {
        when:
        def standardFields = Arrays.asList('$staticClassInfo', '__$stMC',
                'metaClass', 'this$0', '$staticClassInfo$', '$callSiteArray')
        def attributes = inspector.getDataObjects().getChildren()
        then:
        standardFields.forEach({
            standardField ->
                attributes.forEach({
                    attribute -> standardField != attribute
                })
        })
    }

}
