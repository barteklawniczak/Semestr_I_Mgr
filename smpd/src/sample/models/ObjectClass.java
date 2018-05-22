package sample.models;

import java.util.ArrayList;
import java.util.List;

public class ObjectClass {

    private String name;
    private List<Serie> serieList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Serie> getSerieList() {
        return serieList;
    }

    public void setSerieList(List<Serie> serieList) {
        this.serieList = serieList;
    }

    public ObjectClass(String name) {
        this.name = name;
        this.serieList = new ArrayList<>();
    }
}
