package sample.models;

import java.util.ArrayList;
import java.util.List;

public class ObjectClass {

    private String name;
    private double[][] values;
    private double[] averages;
    private double[] standardDeviations;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double[][] getValues() {
        return values;
    }

    public void setValues(double[][] values) {
        this.values = values;
    }

    public double[] getAverages() {
        return averages;
    }

    public void setAverages(double[] averages) {
        this.averages = averages;
    }

    public double[] getStandardDeviations() {
        return standardDeviations;
    }

    public void setStandardDeviations(double[] standardDeviations) {
        this.standardDeviations = standardDeviations;
    }

    public ObjectClass(String name, int amountOfProperties, int counter) {
        this.name = name;
        this.values = new double[amountOfProperties][counter];
        this.averages = new double[amountOfProperties];
        this.standardDeviations = new double[amountOfProperties];
    }
}
