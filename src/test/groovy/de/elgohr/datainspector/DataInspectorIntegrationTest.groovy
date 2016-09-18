package de.elgohr.datainspector

import groovy.json.JsonSlurper
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.boot.context.embedded.LocalServerPort
import org.springframework.boot.test.IntegrationTest
import org.springframework.boot.test.SpringApplicationConfiguration
import org.springframework.boot.test.SpringApplicationContextLoader
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.context.web.WebAppConfiguration
import spock.lang.Specification

import javax.persistence.Entity

@ContextConfiguration(
        loader = SpringApplicationContextLoader.class,
        classes = SpringBootStarter.class)
@WebAppConfiguration
@IntegrationTest
class DataInspectorIntegrationTest extends Specification {

    @Entity
    class TestEntity {
        private String name
        int age
    }

    @LocalServerPort
    def port

    JsonSlurper jsonSlurper

    def setup() {
        jsonSlurper = new JsonSlurper()
    }

    def "should publish the entities on an endpoint in json format" () {
        given:
        def url = new URL("http://localhost:${port}/data")
        when:
        def response = jsonSlurper.parse(url)
        then:
        response.contains("TestEntity")
        response.contains("name")
        response.contains("age")
    }
}
