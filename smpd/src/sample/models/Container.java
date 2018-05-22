package sample.models;

import java.util.ArrayList;
import java.util.List;

public class Container {

    private List<ObjectClass> objectClasses;

    public List<ObjectClass> getObjectClasses() {
        return objectClasses;
    }

    public void setObjectClasses(List<ObjectClass> objectClasses) {
        this.objectClasses = objectClasses;
    }

    public Container() {
        this.objectClasses = new ArrayList<>();
    }
}
