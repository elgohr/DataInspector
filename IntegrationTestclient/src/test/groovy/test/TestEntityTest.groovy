package test

import spock.lang.Specification

import javax.persistence.Entity

class TestEntityTest extends Specification {

    def testEntity

    def setup() {
        testEntity = new TestEntity()
    }

    def "TestEntity must be a javax.persistence.Entity" () {
        when:
        def classAnnotations = testEntity.class.annotations
        then:
        classAnnotations.each {
            it -> it.class.name == Entity.class.name
        }
    }

    def "TestEntity must contain fields" () {
        when:
        def declaredFields = testEntity.class.getDeclaredFields()
        then:
        declaredFields.size() > 0
    }

}
