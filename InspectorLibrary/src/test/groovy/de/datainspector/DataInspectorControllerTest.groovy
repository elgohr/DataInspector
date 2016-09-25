package de.elgohr.datainspector

import de.datainspector.DataInspectorController
import de.datainspector.persistence.JpaEntityInspector
import spock.lang.Specification

class DataInspectorControllerTest extends Specification {

    def "should publish the entities on an endpoint"() {
        given:
        def entityInspector = Mock(JpaEntityInspector)
        def entityInspectorController = new DataInspectorController(entityInspector)
        when:
        HashMap response = entityInspectorController.publishInspectedData()
        then:
        1 * entityInspector.getAttributesPerClass() >> {
            def result = new HashMap<>()
            def attributes = new LinkedList()
            attributes.add("attribute1")
            attributes.add("attribute2")
            result.put("class1", attributes)
            return result
        }
        response.containsKey("class1")
        response.get("class1").contains("attribute1")
        response.get("class1").contains("attribute2")
    }
}
