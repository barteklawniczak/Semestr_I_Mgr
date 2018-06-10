package sample.models;

public class Serie {

    private String name;
    private double[] values;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double[] getValues() {
        return values;
    }

    public void setValues(double[] values) {
        this.values = values;
    }

    public Serie(String name) {
        this.name = name;
    }
}
