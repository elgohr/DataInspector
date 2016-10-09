package test

import groovy.json.JsonSlurper
import org.springframework.boot.context.embedded.LocalServerPort
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.test.context.ActiveProfiles
import org.springframework.web.client.RestTemplate
import spock.lang.Specification

@SpringBootTest(classes = IntegratonAppl.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("integration")
class LibraryIntegrationTest extends Specification {

    @LocalServerPort
    int port

    def "/data endpoint should be available with the maven library and annotation"() {
        when:
        String url = "http://localhost:${port}/data"
        def response = new RestTemplate().getForEntity(url, null, String.class)

        then:
        response.statusCode == HttpStatus.OK
    }

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
