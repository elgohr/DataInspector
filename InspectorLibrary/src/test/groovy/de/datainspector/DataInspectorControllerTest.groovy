import de.datainspector.DataInspectorController
import de.datainspector.businessobject.DataObject
import de.datainspector.persistence.JpaEntityInspector
import groovy.json.JsonSlurper
import spock.lang.Specification

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

}
