import de.datainspector.DataInspectorController
import de.datainspector.persistence.JpaEntityInspector
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class DataInspectorIntegrationTest extends Specification {

    def "should publish the entities on an endpoint in json format"() {
        given:
        def entityInspector = Mock(JpaEntityInspector)
        def mockMvc = MockMvcBuilders
                .standaloneSetup(new DataInspectorController(entityInspector))
                .build()
        def entityMockData = ["class1": ["attribute1", "attribute2"] as LinkedList] as HashMap

        def expectedJson = "{\"persistence\":{\"class1\":[\"attribute1\",\"attribute2\"]}}"

        when:
        def response = mockMvc.perform(get('/data'))

        then:
        1 * entityInspector.getAttributesPerClass() >> {
            return entityMockData
        }
        response
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(content().json(expectedJson))
    }
}
