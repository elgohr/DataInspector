package test

import groovy.json.JsonSlurper
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.boot.context.embedded.LocalServerPort
import org.springframework.boot.test.IntegrationTest
import org.springframework.boot.test.SpringApplicationConfiguration
import org.springframework.http.HttpStatus
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.web.client.RestTemplate
import spock.lang.Specification

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = IntegratonAppl.class)
@WebAppConfiguration
@IntegrationTest("server.port:0")
@ActiveProfiles("integration")
class LibraryIntegrationTest extends Specification {

    @LocalServerPort
    int port

    @Test
    def "/data endpoint should be available with the maven library and annotation"() {
        when:
        String url = "http://localhost:${port}/data"
        def response = new RestTemplate().getForEntity(url, null, String.class)

        then:
        response.statusCode == HttpStatus.OK
    }

    @Test
    def "should fill in the application name from Spring config"() {
        given:
        def jsonSlurper = new JsonSlurper()
        def url = new URL("http://localhost:${port}/data")

        when:
        def json = jsonSlurper.parse(url)

        then:
        json.name == "IntegrationTestApplication"
    }
}
