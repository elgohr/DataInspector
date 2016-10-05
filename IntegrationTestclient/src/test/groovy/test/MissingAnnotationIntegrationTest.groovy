package test

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.boot.context.embedded.LocalServerPort
import org.springframework.boot.test.IntegrationTest
import org.springframework.boot.test.SpringApplicationConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.RestTemplate
import spock.lang.Specification

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = IntegratonAppl.class)
@WebAppConfiguration
@IntegrationTest("server.port:0")
class MissingAnnotationIntegrationTest extends Specification {

    @LocalServerPort
    int port

    @Test
    def "/data endpoint should not be present without annotation"() {
        when:
        String url = "http://localhost:${port}/data"
        def response = new RestTemplate().getForEntity(url, null, String.class)

        then:
        thrown(HttpClientErrorException)
    }

}
