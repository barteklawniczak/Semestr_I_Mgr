package sample.models;

import java.util.ArrayList;
import java.util.List;

public class Container {

    private List<Serie> serieList;
    private ObjectClass acer;
    private ObjectClass quercus;

    public List<Serie> getSerieList() {
        return serieList;
    }

    public void setSerieList(List<Serie> serieList) {
        this.serieList = serieList;
    }

    public ObjectClass getAcer() {
        return acer;
    }

    public void setAcer(ObjectClass acer) {
        this.acer = acer;
    }

    public ObjectClass getQuercus() {
        return quercus;
    }

    public void setQuercus(ObjectClass quercus) {
        this.quercus = quercus;
    }

    public Container() {
        this.serieList = new ArrayList<>();
    }
}
