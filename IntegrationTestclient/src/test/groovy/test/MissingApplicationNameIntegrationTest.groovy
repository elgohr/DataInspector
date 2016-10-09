package test

import groovy.json.JsonSlurper
import org.springframework.boot.context.embedded.LocalServerPort
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification

@SpringBootTest(classes = IntegratonAppl.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("noApplicationName")
class MissingApplicationNameIntegrationTest extends Specification {

    @LocalServerPort
    int port

    def "should not crash, if the application name was not set"() {
        given:
        def jsonSlurper = new JsonSlurper()
        def url = new URL("http://localhost:${port}/data")

        when:
        def json = jsonSlurper.parse(url)

        then:
        json.name == "application"
    }

}
