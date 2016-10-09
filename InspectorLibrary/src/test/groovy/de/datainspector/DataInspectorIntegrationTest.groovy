import de.datainspector.DataInspectorController
import de.datainspector.businessobject.DataObject
import de.datainspector.persistence.JpaEntityInspector
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class DataInspectorIntegrationTest extends Specification {

    def entityInspector
    def dataInspectorController
    def mockMvc

    def setup() {
        entityInspector = Mock(JpaEntityInspector)
        dataInspectorController = new DataInspectorController(entityInspector)
        mockMvc = MockMvcBuilders
                .standaloneSetup(dataInspectorController)
                .build()
    }

    def "should publish the entities on an endpoint in json format"() {
        when:
        def response = mockMvc.perform(get('/data'))

        then:
        1 * entityInspector.getDataObjects() >> {
            DataObject inspectorObject = new DataObject("persistence")
            DataObject classLayer = new DataObject("class1")
            classLayer.addChild(new DataObject("attribute1"))
            classLayer.addChild(new DataObject("attribute2"))
            inspectorObject.addChild(classLayer)
            return inspectorObject
        }
        response
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(content()
                .json(
                '{"name": "application",' +
                        '  "children": [{' +
                        '    "name": "persistence",' +
                        '    "children": [{' +
                        '      "name": "class1",' +
                        '      "children": [{' +
                        '        "name": "attribute1"' +
                        '      }, {' +
                        '        "name": "attribute2"' +
                        '      }]' +
                        '    }]' +
                        '  }]' +
                        '}'))
    }

    def "should return http 500, when exception occurs" () {
        when:
        def response = mockMvc.perform(get('/data'))
        then:
        1 * entityInspector.getDataObjects() >> {
            throw new IOException()
        }
        response
                .andExpect(status().is5xxServerError())
    }
}
