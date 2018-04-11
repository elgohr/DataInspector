package de.datainspector;

import java.io.IOException;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import de.datainspector.businessobject.DataObject;
import de.datainspector.persistence.JpaEntityInspector;

@RestController
public class DataInspectorController {

	private JpaEntityInspector jpaEntityInspector;
	private ObjectMapper objectMapper;

	@Value("${spring.application.name:application}")
	private String applicationName = "application";

	@Autowired
	DataInspectorController(JpaEntityInspector jpaEntityInspector) {
		this.jpaEntityInspector = jpaEntityInspector;
		this.objectMapper = new ObjectMapper();
	}

	@RequestMapping(
			value = "/data",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public String publishInspectedData() throws IOException {
		DataObject applicationObject = new DataObject(applicationName);
		applicationObject.addChild(jpaEntityInspector.getDataObjects());
		return objectMapper.writeValueAsString(applicationObject);
	}

	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(IOException.class)
	public void handleIOExceptions() {
	}
}
