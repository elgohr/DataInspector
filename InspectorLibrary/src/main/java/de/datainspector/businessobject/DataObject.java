package de.datainspector.businessobject;

import java.util.ArrayList;

public class DataObject {

    private final String name;
    private ArrayList<DataObject> children = new ArrayList<>();

    public DataObject(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void addChild(DataObject child) {
        children.add(child);
    }

    public ArrayList<DataObject> getChildren() {
        return children;
    }

}
