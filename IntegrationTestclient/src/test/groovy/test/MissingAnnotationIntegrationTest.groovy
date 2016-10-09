package test

import org.springframework.boot.context.embedded.LocalServerPort
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.RestTemplate
import spock.lang.Specification

@SpringBootTest(classes = IntegratonAppl.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MissingAnnotationIntegrationTest extends Specification {

    @LocalServerPort
    int port

    def "/data endpoint should not be present without annotation"() {
        when:
        String url = "http://localhost:${port}/data"
        def response = new RestTemplate().getForEntity(url, null, String.class)

        then:
        thrown(HttpClientErrorException)
    }

}
