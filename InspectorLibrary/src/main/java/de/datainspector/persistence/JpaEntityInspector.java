package de.datainspector.persistence;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;

import org.reflections.Reflections;
import org.springframework.stereotype.Component;

import de.datainspector.AbstractDataInspector;
import de.datainspector.businessobject.DataObject;

@Component
public class JpaEntityInspector extends AbstractDataInspector {

	private final static String inspectorName = "persistence";
	private final List blockedFields = Arrays.asList(
			"$staticClassInfo",
			"__$stMC",
			"metaClass",
			"this$0",
			"$staticClassInfo$",
			"$callSiteArray",
			"$jacocoData"
	);
	private final Set<Class<?>> entityClasses = new Reflections("")
			.getTypesAnnotatedWith(Entity.class);

	@Override
	public DataObject getDataObjects() {
		DataObject inspectorObject = new DataObject(inspectorName);
		entityClasses.forEach((entityClass) -> {
			DataObject entityDataObject = new DataObject(entityClass.getName());
			for (Field declaredField : entityClass.getDeclaredFields()) {
				if (!blockedFields.contains(declaredField.getName())) {
					DataObject fieldDataObject = new DataObject(declaredField.getName());
					entityDataObject.addChild(fieldDataObject);
				}
			}
			inspectorObject.addChild(entityDataObject);
		});
		return inspectorObject;
	}

}
