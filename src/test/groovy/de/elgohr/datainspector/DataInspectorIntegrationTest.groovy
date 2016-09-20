package de.elgohr.datainspector

import net.minidev.json.JSONArray
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

class DataInspectorIntegrationTest extends Specification {

    def "should publish the entities on an endpoint in json format" () {
        given:
        def entityInspector = Mock(EntityInspector)
        def mockMvc = MockMvcBuilders.standaloneSetup(
                new DataInspectorController(entityInspector: entityInspector)
        ).build()

        def expectedValues = new JSONArray()
        expectedValues.add("attribute1")
        expectedValues.add("attribute2")

        when:
        def response = mockMvc.perform(get('/data'))

        then:
        1 * entityInspector.getAttributesPerClass() >> {
            def result = new HashMap<>()
            def attributes = new LinkedList()
            attributes.add("attribute1")
            attributes.add("attribute2")
            result.put("class1", attributes)
            return result
        }
        response
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath('$.class1').value(expectedValues))
    }
}
