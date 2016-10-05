package test

import groovy.json.JsonSlurper
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.boot.context.embedded.LocalServerPort
import org.springframework.boot.test.IntegrationTest
import org.springframework.boot.test.SpringApplicationConfiguration
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.springframework.test.context.web.WebAppConfiguration
import spock.lang.Specification

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = IntegratonAppl.class)
@WebAppConfiguration
@IntegrationTest("server.port:0")
@ActiveProfiles("noApplicationName")
class MissingApplicationNameIntegrationTest extends Specification {

    @LocalServerPort
    int port

    @Test
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
