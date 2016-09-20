package de.elgohr.datainspector

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

@RestController
class DataInspectorController {

    @Autowired
    EntityInspector entityInspector

    @RequestMapping(value="/data", method=RequestMethod.GET)
    Map<String, LinkedList<String>> publishData() {
        return entityInspector.getAttributesPerClass()
    }
}
