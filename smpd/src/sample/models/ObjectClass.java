package sample.models;

import java.util.ArrayList;
import java.util.List;

public class ObjectClass {

    private String name;
    private float[][] values;
    private float[] averages;
    private double[] standardDeviations;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float[][] getValues() {
        return values;
    }

    public void setValues(float[][] values) {
        this.values = values;
    }

    public float[] getAverages() {
        return averages;
    }

    public void setAverages(float[] averages) {
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
        this.values = new float[amountOfProperties][counter];
        this.averages = new float[amountOfProperties];
        this.standardDeviations = new double[amountOfProperties];
    }
}
