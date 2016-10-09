import de.datainspector.DataInspectorController
import de.datainspector.businessobject.DataObject
import de.datainspector.persistence.JpaEntityInspector
import groovy.json.JsonSlurper
import org.springframework.http.HttpStatus
import spock.lang.Specification

import javax.servlet.http.HttpServletResponse

class DataInspectorControllerTest extends Specification {

    def entityInspector
    def entityInspectorController

    def setup() {
        entityInspector = Mock(JpaEntityInspector)
        entityInspectorController = new DataInspectorController(entityInspector)
    }

    def "should publish the entities on an endpoint"() {
        given:
        def jsonSlurper = new JsonSlurper()

        when:
        def json = jsonSlurper.parseText(entityInspectorController.publishInspectedData())

        then:
        1 * entityInspector.getDataObjects() >> {
            DataObject inspectorObject = new DataObject("persistence")
            DataObject classLayer = new DataObject("class1")
            classLayer.addChild(new DataObject("attribute1"))
            classLayer.addChild(new DataObject("attribute2"))
            inspectorObject.addChild(classLayer)
            return inspectorObject
        }
        json.name == "application"
        json.children[0].name == "persistence"
        json.children[0].children[0].name == "class1"
        json.children[0].children[0].children[0].name == "attribute1"
        json.children[0].children[0].children[1].name == "attribute2"
    }

    def "should return http 500 on exception" () {
        given:
        def servletResponse = Mock(HttpServletResponse)
        when:
        entityInspectorController.handleExceptions(servletResponse)
        then:
        1 * servletResponse.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value())
    }
}
