package de.elgohr.datainspector

import spock.lang.Specification

import javax.persistence.Entity

class EntityInspectorTest extends Specification {

    def inspector = new EntityInspector()

    class TestClass {
        // This class should be irrelevant for the scan
    }

    @Entity
    class TestEntity {
        private String name
        int age
    }

    def "should inspect only classes, which are annotated with @Entity"() {
        when:
        def classes = inspector.getClasses()
        then:
        classes.size() == 1
        classes.contains(TestEntity)
    }

    def "should return inspected classes"() {
        when:
        def listOfClasses = inspector.getAttributesPerClass()
        then:
        listOfClasses.size() == 1
        listOfClasses.containsKey(TestEntity.getName())
    }

    def "should return the attributes of the classes in relation"() {
        when:
        def listOfClasses = inspector.getAttributesPerClass()
        def attributes = listOfClasses.get(TestEntity.getName())
        then:
        listOfClasses.size() == 1
        listOfClasses.containsKey(TestEntity.getName())
        attributes.size() == 2
        attributes.contains("name")
        attributes.contains("age")
    }

    def "should not return the standard fields of the classes"() {
        when:
        def standardFields = Arrays.asList('$staticClassInfo', '__$stMC',
                'metaClass', 'this$0', '$staticClassInfo$', '$callSiteArray')
        def listOfClasses = inspector.getAttributesPerClass()
        def attributes = listOfClasses.get(TestEntity.getName())
        then:
        for (def standardField : standardFields) {
            !attributes.contains(standardField)
        }
    }

}
